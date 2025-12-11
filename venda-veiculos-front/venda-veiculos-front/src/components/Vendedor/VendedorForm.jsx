import React, { useState } from 'react';


const API_BASE_URL = 'http://localhost:8081/vendedores';


const VendedorForm = ({ onCadastroSucesso }) => {


    const [formData, setFormData] = useState({
        nome: '',
        cpf: '',
        telefone: '',
        email: '',
        endereco: '',
        idVendedor: '',
        comissao: 0.0,
        dataContrato: '2025-01-01'
    });
    const [message, setMessage] = useState('');


    const handleChange = (e) => {
        const { name, value } = e.target;

        setFormData(prevData => ({
            ...prevData,
            [name]: name === 'comissao' ? parseFloat(value) || 0 : value
        }));
    };


    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage('Enviando dados...');

        try {
            const response = await fetch(API_BASE_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(`Falha no cadastro. Status: ${response.status}. Detalhes: ${errorData.message || 'Erro desconhecido.'}`);
            }


            const novoVendedor = await response.json();
            setMessage(`Vendedor ${novoVendedor.nome} cadastrado com sucesso! ID: ${novoVendedor.id}`);


            if (onCadastroSucesso) {
                onCadastroSucesso();
            }


            setFormData({
                nome: '', cpf: '', telefone: '', email: '', endereco: '', idVendedor: '', comissao: 0.0, dataContrato: '2025-01-01'
            });

        } catch (error) {
            console.error("Erro ao enviar:", error);
            setMessage(`Erro: ${error.message}. Verifique o console e o Backend.`);
        }
    };

    return (
        <div style={{border: '1px solid #ccc', padding: '15px', margin: '20px'}}>
            <h3>Cadastrar Novo Vendedor</h3>
            <form onSubmit={handleSubmit} style={{display: 'flex', flexDirection: 'column', gap: '10px'}}>
                {/* ... (Seus Inputs permanecem aqui) ... */}
                <input type="text" name="nome" value={formData.nome} onChange={handleChange} placeholder="Nome" required />
                <input type="text" name="cpf" value={formData.cpf} onChange={handleChange} placeholder="CPF" required />
                <input type="text" name="telefone" value={formData.telefone} onChange={handleChange} placeholder="Telefone" />
                <input type="email" name="email" value={formData.email} onChange={handleChange} placeholder="Email" />
                <input type="text" name="endereco" value={formData.endereco} onChange={handleChange} placeholder="Endereço" />
                <input type="text" name="idVendedor" value={formData.idVendedor} onChange={handleChange} placeholder="ID Vendedor" required />
                <input type="number" name="comissao" value={formData.comissao} onChange={handleChange} placeholder="Comissão (%)" step="0.01" required />
                <label>Data Contrato: <input type="date" name="dataContrato" value={formData.dataContrato} onChange={handleChange} required /></label>

                <button type="submit">Cadastrar Vendedor</button>
            </form>
            <p style={{marginTop: '10px'}}>{message}</p>
        </div>
    );
};

export default VendedorForm;