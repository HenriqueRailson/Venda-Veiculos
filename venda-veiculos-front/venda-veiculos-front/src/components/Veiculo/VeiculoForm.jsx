import React, { useState } from 'react';

const API_BASE_URL = 'http://localhost:8081/veiculos';

const VeiculoForm = ({ onCadastroSucesso }) => {

    const [message, setMessage] = useState('');
    const [formData, setFormData] = useState({
        tipo: 'Carro', //
        modelo: '',
        marca: '',
        cor: '',
        anoFabricacao: '',
        chassi: '',
        precoCompra: '',
        numeroPortas: '', //
        tipoCarro: 'Sedan', //
        cilindrada: '', //
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevData => ({
            ...prevData,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage('Cadastrando Veículo...');


        const precoString = String(formData.precoCompra).replace(',', '.');
        const precoFinal = parseFloat(precoString);


        const anoFabricacaoInt = parseInt(formData.anoFabricacao, 10);
        const numeroPortasInt = formData.tipo === 'Carro' ? parseInt(formData.numeroPortas, 10) : null;
        const cilindradaInt = formData.tipo === 'Moto' ? parseInt(formData.cilindrada, 10) : null;


        if (isNaN(precoFinal) || precoFinal <= 0) {
            setMessage("Erro: O preço deve ser um número válido.");
            return;
        }
        if (isNaN(anoFabricacaoInt)) {
             setMessage("Erro: Ano de fabricação inválido.");
             return;
        }


        const veiculoRequest = {
            tipo: formData.tipo.toUpperCase(), // "CARRO" ou "MOTO"
            modelo: formData.modelo,
            marca: formData.marca,
            cor: formData.cor,
            anoFabricacao: anoFabricacaoInt,
            chassi: formData.chassi,
            precoCompra: precoFinal,


            numeroPortas: numeroPortasInt,
            tipoCarro: formData.tipo === 'Carro' ? formData.tipoCarro : null,
            cilindrada: cilindradaInt
        };

        try {
            const response = await fetch(API_BASE_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(veiculoRequest),
            });

            if (!response.ok) {
                const errorBody = await response.text();

                throw new Error(`Falha no cadastro. Status: ${response.status}. Detalhes: ${errorBody.substring(0, 150)}...`);
            }

            setMessage(`Veículo cadastrado com sucesso!`);


            setFormData({
                tipo: 'Carro',
                modelo: '',
                marca: '',
                cor: '',
                anoFabricacao: '',
                chassi: '',
                precoCompra: '',
                numeroPortas: '',
                tipoCarro: 'Sedan',
                cilindrada: ''
            });

            if (onCadastroSucesso) onCadastroSucesso();

        } catch (error) {
            console.error("Erro ao enviar veículo:", error);
            setMessage(`Erro: ${error.message}`);
        }
    };

    return (
        <div style={{ padding: '15px' }}>
            <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>

                {/* Seleção do Tipo de Veículo */}
                <label>
                    Tipo:
                    <select name="tipo" value={formData.tipo} onChange={handleChange} style={{marginLeft: '10px', padding: '5px'}}>
                        <option value="Carro">Carro</option>
                        <option value="Moto">Moto</option>
                    </select>
                </label>

                <input type="text" name="modelo" value={formData.modelo} onChange={handleChange} placeholder="Modelo " required />
                <input type="text" name="marca" value={formData.marca} onChange={handleChange} placeholder="Marca " required />
                <input type="text" name="cor" value={formData.cor} onChange={handleChange} placeholder="Cor " required />

                <input type="number" name="anoFabricacao" value={formData.anoFabricacao} onChange={handleChange} placeholder="Ano Fabricação " required />

                <input type="text" name="chassi" value={formData.chassi} onChange={handleChange} placeholder="Chassi " required />

                {/* Campo de Preço como texto para permitir vírgula */}
                <input type="text" name="precoCompra" value={formData.precoCompra} onChange={handleChange} placeholder="Preço de Compra " required />

                {/* Campos Específicos de CARRO */}
                {formData.tipo === 'Carro' && (
                    <>
                        <input type="number" name="numeroPortas" value={formData.numeroPortas} onChange={handleChange} placeholder="Número de Portas " required />
                        <input type="text" name="tipoCarro" value={formData.tipoCarro} onChange={handleChange} placeholder="Tipo Carro " />
                    </>
                )}

                {/* Campos Específicos de MOTO */}
                {formData.tipo === 'Moto' && (
                    <input type="number" name="cilindrada" value={formData.cilindrada} onChange={handleChange} placeholder="Cilindrada (cc)" required />
                )}

                <button type="submit" style={{marginTop: '10px'}}>Cadastrar Veículo</button>
            </form>

            {/* Mensagem de Feedback */}
            {message && (
                <p style={{
                    marginTop: '10px',
                    padding: '10px',
                    backgroundColor: message.startsWith('Erro') || message.startsWith('Falha') ? '#442222' : '#224422',
                    color: message.startsWith('Erro') || message.startsWith('Falha') ? '#ffaaaa' : '#aaffaa',
                    border: '1px solid',
                    borderColor: message.startsWith('Erro') || message.startsWith('Falha') ? '#ff5555' : '#55ff55',
                    borderRadius: '5px'
                }}>
                    {message}
                </p>
            )}
        </div>
    );
};

export default VeiculoForm;