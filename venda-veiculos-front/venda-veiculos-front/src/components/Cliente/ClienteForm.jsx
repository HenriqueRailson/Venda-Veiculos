import React, { useState } from 'react';

const API_BASE_URL = 'http://localhost:8081/clientes';

const ClienteForm = ({ onCadastroSucesso }) => {
    const [formData, setFormData] = useState({
        nome: '',
        cpf: '',
        telefone: '',
        email: '',
        endereco: '',
    });
    const [message, setMessage] = useState('');

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevData => ({
            ...prevData,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage('Enviando dados...');

        try {
            const response = await fetch(API_BASE_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(formData),
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(`Falha no cadastro. Detalhes: ${errorData.message || response.statusText}`);
            }

            const novoCliente = await response.json();
            setMessage(`Cliente ${novoCliente.nome} cadastrado com sucesso! ID: ${novoCliente.id}`);
            setFormData({ nome: '', cpf: '', telefone: '', email: '', endereco: '' });
            
            if (onCadastroSucesso) {
                onCadastroSucesso();
            }

        } catch (error) {
            console.error("Erro ao enviar cliente:", error);
            setMessage(`Erro: ${error.message}.`);
        }
    };

    return (
        <div style={{ padding: '20px', border: '1px solid gray', margin: '20px' }}>
            <h3>Cadastrar Novo Cliente</h3>
            <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
                <input type="text" name="nome" value={formData.nome} onChange={handleChange} placeholder="Nome" required />
                <input type="text" name="cpf" value={formData.cpf} onChange={handleChange} placeholder="CPF" required />
                <input type="text" name="telefone" value={formData.telefone} onChange={handleChange} placeholder="Telefone" />
                <input type="email" name="email" value={formData.email} onChange={handleChange} placeholder="Email" />
                <input type="text" name="endereco" value={formData.endereco} onChange={handleChange} placeholder="Endereço" />
                
                <button type="submit">Cadastrar Cliente</button>
            </form>
            <p style={{ color: message.startsWith('Erro') ? 'red' : 'green' }}>{message}</p>
        </div>
    );
};

export default ClienteForm;