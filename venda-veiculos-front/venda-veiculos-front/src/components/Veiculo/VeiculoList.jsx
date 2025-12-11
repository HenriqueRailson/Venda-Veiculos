import React, { useState, useEffect } from 'react';

const API_BASE_URL = 'http://localhost:8081/veiculos';

const VeiculoList = ({ key, onVeiculoDeleted, onCadastroSucesso }) => {
    const [veiculos, setVeiculos] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchVeiculos = async () => {
        try {
            const response = await fetch(API_BASE_URL);

            if (!response.ok) {
                throw new Error(`Erro na API. Status: ${response.status}`);
            }

            const data = await response.json();
            setVeiculos(data);

        } catch (err) {
            console.error("Erro ao carregar veículos:", err);
            setError("Não foi possível carregar os veículos do Backend.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchVeiculos();
    }, [key]);

    const handleDelete = async (id) => {
        if (!window.confirm(`Tem certeza que deseja excluir o Veículo ID ${id}?`)) {
            return;
        }

        try {
            const response = await fetch(`${API_BASE_URL}/${id}`, {
                method: 'DELETE',
            });

            if (response.status === 204 || response.status === 200) {
                alert(`Veículo ID ${id} excluído com sucesso.`);

                fetchVeiculos();

                if (onVeiculoDeleted) onVeiculoDeleted();
            } else if (response.status === 404) {
                 alert('Veículo não encontrado.');
            } else {
                alert(`Falha ao excluir. Status: ${response.status}`);
            }

        } catch (error) {
            console.error("Erro na exclusão:", error);
            alert("Erro de conexão ao tentar excluir.");
        }
    };
    // -----------------------------------


    if (loading) return <div style={{padding: '10px'}}>Carregando lista de veículos...</div>;
    if (error) return <div style={{color: 'red', padding: '10px'}}>{error}</div>;

    return (
        <div style={{border: '1px solid #ccc', padding: '15px', margin: '20px'}}>
            <h2>Estoque de Veículos ({veiculos.length} itens)</h2>
            {veiculos.length === 0 ? (
                <p>Nenhum veículo encontrado no estoque.</p>
            ) : (
                <ul style={{listStyle: 'none', padding: 0}}>
                    {veiculos.map(veiculo => (
                        <li key={veiculo.id} style={{marginBottom: '10px', borderBottom: '1px dotted #ccc', paddingBottom: '5px', display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>
                            <div>
                                <strong>ID:</strong> {veiculo.id} |
                                <strong> Tipo:</strong> {veiculo['@class'] ? veiculo['@class'].split('.').pop() : 'N/D'} |
                                <strong> Marca:</strong> {veiculo.marca} {veiculo.modelo} |
                                <strong> Status:</strong> {veiculo.statusVeiculo}
                            </div>
                            <div style={{display: 'flex', gap: '8px'}}>
                                {/* Botão de Edição (PUT) */}
                                <button style={{backgroundColor: '#FF9800', color: 'white', border: 'none', padding: '5px 10px', cursor: 'pointer'}}
                                    onClick={() => alert(`Funcionalidade de Edição (PUT) para ID: ${veiculo.id}`)}>
                                    Editar
                                </button>
                                {/* Botão de Exclusão (DELETE) */}
                                <button style={{backgroundColor: '#E91E63', color: 'white', border: 'none', padding: '5px 10px', cursor: 'pointer'}}
                                    onClick={() => handleDelete(veiculo.id)}>
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

export default VeiculoList;