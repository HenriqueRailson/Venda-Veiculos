import React, { useState, useEffect } from 'react';

const API_BASE_URL = 'http://localhost:8081/vendedores';

const VendedorList = ({ key, onVendedorDeleted }) => {
    const [vendedores, setVendedores] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);


    const fetchVendedores = async () => {
        try {
            const response = await fetch(API_BASE_URL);
            if (!response.ok) {
                throw new Error(`Erro na API. Status: ${response.status}`);
            }
            const data = await response.json();
            setVendedores(data);
        } catch (err) {
            console.error("Erro ao carregar dados:", err);
            setError("Não foi possível conectar ao Backend Java.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchVendedores();
    }, [key]);


    const handleDelete = async (id, nome) => {
        if (!window.confirm(`Tem certeza que deseja excluir o vendedor ${nome} (ID: ${id})?`)) {
            return;
        }

        try {
            const response = await fetch(`${API_BASE_URL}/${id}`, {
                method: 'DELETE',
            });

            if (response.status === 204 || response.status === 200) {
                alert(`Vendedor ${nome} excluído com sucesso.`);


                fetchVendedores();


                if (onVendedorDeleted) onVendedorDeleted();
            } else if (response.status === 404) {
                 alert('Vendedor não encontrado.');
            } else {
                alert(`Falha ao excluir. Status: ${response.status}`);
            }

        } catch (error) {
            console.error("Erro na exclusão:", error);
            alert("Erro de conexão ao tentar excluir.");
        }
    };


    if (loading) return <div style={{padding: '20px'}}>Carregando vendedores...</div>;
    if (error) return <div style={{color: 'red', padding: '20px'}}>{error}</div>;

    return (
        <div style={{border: '1px solid #ccc', padding: '15px', margin: '20px'}}>
            <h2>Lista de Vendedores</h2>
            {vendedores.length === 0 ? (
                <p>Nenhum vendedor encontrado.</p>
            ) : (
                <ul style={{listStyle: 'none', padding: 0}}>
                    {vendedores.map(vendedor => (
                        <li key={vendedor.id} style={{marginBottom: '10px', borderBottom: '1px dotted #eee', paddingBottom: '5px', display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>
                            <div>
                                <strong>ID:</strong> {vendedor.id} |
                                <strong> {vendedor.nome}</strong> |
                                <strong> Comissão:</strong> {vendedor.comissao}%
                            </div>
                            <div style={{display: 'flex', gap: '8px'}}>
                                {/* NOTA: A funcionalidade de edição (PUT) seria complexa em um formulário pop-up.
                                Por enquanto, o botão apenas alerta o ID. */}
                                <button style={{backgroundColor: '#FF9800', color: 'white', border: 'none', padding: '5px 10px', cursor: 'pointer'}}
                                    onClick={() => alert(`Funcionalidade de Edição (PUT) para ID: ${vendedor.id}`)}>
                                    Editar
                                </button>
                                <button style={{backgroundColor: '#E91E63', color: 'white', border: 'none', padding: '5px 10px', cursor: 'pointer'}}
                                    onClick={() => handleDelete(vendedor.id, vendedor.nome)}>
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

export default VendedorList;