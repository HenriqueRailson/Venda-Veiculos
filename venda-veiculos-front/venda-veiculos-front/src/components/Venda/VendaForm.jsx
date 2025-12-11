import React, { useState } from 'react';

const API_BASE_URL = 'http://localhost:8081/vendas';

const VendaForm = ({ onCadastroSucesso }) => {

    const [message, setMessage] = useState('');
    const [formData, setFormData] = useState({
        clienteId: '',
        vendedorId: '',
        veiculoId: '',
        valorFinal: '',
        formaPagamento: 'Cartão',
        dataVenda: new Date().toISOString().slice(0, 10),
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevData => ({
            ...prevData,
            [name]: value
        }));
    };


    const iniciarDownloadPdf = async (vendaId) => {
        try {
            const response = await fetch(`${API_BASE_URL}/${vendaId}/pdf`);

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`Falha ao gerar PDF. Status: ${response.status}. Detalhes: ${errorText.substring(0, 100)}`);
            }

            const blob = await response.blob();
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = `NotaFiscal_Venda_${vendaId}.pdf`;
            document.body.appendChild(a);
            a.click();
            a.remove();
            window.URL.revokeObjectURL(url);

            setMessage(`Venda registrada e Nota Fiscal (ID ${vendaId}) baixada com sucesso!`);

        } catch (error) {
            console.error("Erro ao baixar NF:", error);
            setMessage(`Venda registrada, mas Erro ao baixar NF: ${error.message}`);
        }
    };


    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage('Registrando Venda...');

        const parseId = (idValue) => idValue ? parseInt(idValue, 10) : null;

        const clienteId = parseId(formData.clienteId);
        const vendedorId = parseId(formData.vendedorId);
        const veiculoId = parseId(formData.veiculoId);

        const valorString = String(formData.valorFinal).replace(',', '.');
        const valorFinal = parseFloat(valorString);

        if (clienteId === null || vendedorId === null || veiculoId === null || isNaN(valorFinal) || valorFinal <= 0) {
             setMessage('Erro: Todos os IDs e o Valor Final devem ser preenchidos e válidos.');
             return;
        }


        const vendaRequest = {
            clienteId: clienteId,
            vendedorId: vendedorId,
            veiculoId: veiculoId,
            valorFinal: valorFinal,
            formaPagamento: formData.formaPagamento,
            dataVenda: formData.dataVenda,
        };

        try {
            const response = await fetch(API_BASE_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(vendaRequest),
            });

            if (!response.ok) {

                let errorDetails = "Erro desconhecido.";
                try {

                    const errorJson = await response.json();
                    if (errorJson && errorJson.detalhes) {
                        errorDetails = errorJson.detalhes;
                    } else if (errorJson && errorJson.message) {
                        errorDetails = errorJson.message;
                    } else {

                        errorDetails = await response.text();
                    }
                } catch (e) {

                    errorDetails = `Falha de conexão. Status: ${response.status}`;
                }

                throw new Error(`Falha na Venda. Detalhes: ${errorDetails.substring(0, 200)}...`);
            }


            const contentType = response.headers.get("content-type");
            let savedVenda = null;

            if (contentType && contentType.includes("application/json")) {

                savedVenda = await response.json();

                if (savedVenda && savedVenda.id) {
                    await iniciarDownloadPdf(savedVenda.id);
                } else {
                    setMessage(`Venda registrada com sucesso! (ID não retornado, NF não gerada)`);
                }
            } else {

                setMessage(`Venda registrada com sucesso! (Verifique o banco para confirmação)`);
            }




            setFormData({
                clienteId: '',
                vendedorId: '',
                veiculoId: '',
                valorFinal: '',
                formaPagamento: 'Cartão',
                dataVenda: new Date().toISOString().slice(0, 10),
            });

            if (onCadastroSucesso) onCadastroSucesso();

        } catch (error) {
            console.error("Erro ao registrar venda:", error);
            setMessage(`Erro ao registrar venda: ${error.message}`);
        }
    };

    return (
        <div style={{ padding: '20px', border: '1px solid gray', margin: '20px' }}>
            <h3>Registrar Nova Venda</h3>
            <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
                <input type="number" name="clienteId" value={formData.clienteId} onChange={handleChange} placeholder="ID Cliente (Ex: 1)" required />
                <input type="number" name="vendedorId" value={formData.vendedorId} onChange={handleChange} placeholder="ID Vendedor (Ex: 1)" required />
                <input type="number" name="veiculoId" value={formData.veiculoId} onChange={handleChange} placeholder="ID Veículo (Ex: 1)" required />

                <input type="text" name="valorFinal" value={formData.valorFinal} onChange={handleChange} placeholder="Valor Final da Venda" required />

                <select name="formaPagamento" value={formData.formaPagamento} onChange={handleChange}>
                    <option value="Cartão">Cartão</option>
                    <option value="A vista">À vista</option>
                    <option value="Financiamento">Financiamento</option>
                </select>

                <label>Data Venda: <input type="date" name="dataVenda" value={formData.dataVenda} onChange={handleChange} required /></label>

                <button type="submit">Registrar Venda e Baixar NF</button>
            </form>
            <p style={{ color: message.startsWith('Erro') || message.startsWith('Falha') ? 'red' : 'green' }}>{message}</p>
        </div>
    );
};

export default VendaForm;