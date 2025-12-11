import React, { useState, useEffect } from 'react';


const API_BASE_URL = 'http://localhost:8081/clientes';


const ClienteList = ({ key, onClienteDeleted }) => {
    const [clientes, setClientes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchClientes = async () => {
        try {
            const response = await fetch(API_BASE_URL);

            if (!response.ok) {
                throw new Error(`Erro na API. Status: ${response.status}`);
            }

            const data = await response.json();
            setClientes(data);

        } catch (err) {
            console.error("Erro ao carregar clientes:", err);
            setError("Não foi possível carregar os clientes do Backend.");
        } finally {
            setLoading(false);
        }
    };


    useEffect(() => {
        fetchClientes();
    }, [key]);


    const handleDelete = async (id, nome) => {
        if (!window.confirm(`Tem certeza que deseja excluir o cliente ${nome} (ID: ${id})?`)) {
            return;
        }

        try {
            const response = await fetch(`${API_BASE_URL}/${id}`, {
                method: 'DELETE',
            });


            if (response.status === 204 || response.status === 200) {
                alert(`Cliente ${nome} excluído com sucesso.`);


                fetchClientes();

                if (onClienteDeleted) onClienteDeleted();
            } else if (response.status === 404) {
                 alert('Cliente não encontrado no sistema.');
            } else {
                alert(`Falha ao excluir. Status: ${response.status}`);
            }

        } catch (error) {
            console.error("Erro na exclusão:", error);
            alert("Erro de conexão ao tentar excluir.");
        }
    };
    // -----------------------------------

    if (loading) return <div style={{padding: '10px', color: '#666'}}>Carregando lista de clientes...</div>;
    if (error) return <div style={{color: 'red', padding: '10px'}}>{error}</div>;

    return (
        <div style={{border: '1px solid #ccc', padding: '15px', margin: '20px', borderRadius: '8px', backgroundColor: '#fff'}}>
            <h2 style={{borderBottom: '2px solid #2196F3', paddingBottom: '10px', color: '#333'}}>
                Clientes Cadastrados ({clientes.length})
            </h2>

            {clientes.length === 0 ? (
                <p style={{fontStyle: 'italic', color: '#777'}}>Nenhum cliente encontrado. Utilize o formulário acima para cadastrar.</p>
            ) : (
                <ul style={{listStyle: 'none', padding: 0}}>
                    {clientes.map(cliente => (
                        <li key={cliente.id} style={{
                            marginBottom: '10px',
                            borderBottom: '1px dotted #ccc',
                            paddingBottom: '10px',
                            display: 'flex',
                            justifyContent: 'space-between',
                            alignItems: 'center'
                        }}>
                            <div>
                                <span style={{fontWeight: 'bold', color: '#555'}}>ID: {cliente.id}</span> |
                                <span style={{fontWeight: 'bold', fontSize: '1.1em'}}> {cliente.nome}</span> <br/>
                                <span style={{fontSize: '0.9em', color: '#666'}}>CPF: {cliente.cpf} | Email: {cliente.email || 'N/D'}</span>
                            </div>

                             <div style={{display: 'flex', gap: '8px'}}>
                                {/* Botão de Edição (Simulado) */}
                                <button
                                    style={{
                                        backgroundColor: '#FF9800',
                                        color: 'white',
                                        border: 'none',
                                        padding: '6px 12px',
                                        cursor: 'pointer',
                                        borderRadius: '4px'
                                    }}
                                    onClick={() => alert(`Funcionalidade de Edição (PUT) para ID: ${cliente.id} em desenvolvimento.`)}>
                                    Editar
                                </button>

                                {/* Botão de Exclusão (DELETE) */}
                                <button
                                    style={{
                                        backgroundColor: '#E91E63',
                                        color: 'white',
                                        border: 'none',
                                        padding: '6px 12px',
                                        cursor: 'pointer',
                                        borderRadius: '4px'
                                    }}
                                    onClick={() => handleDelete(cliente.id, cliente.nome)}>
                                    Excluir
                                </button>
                            </div>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default ClienteList;