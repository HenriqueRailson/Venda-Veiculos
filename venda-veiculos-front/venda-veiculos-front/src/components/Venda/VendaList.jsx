import React, { useState, useEffect } from 'react';

const API_BASE_URL = 'http://localhost:8081/vendas';

const VendaList = () => {
    const [vendas, setVendas] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchVendas = async () => {
            try {
                const response = await fetch(API_BASE_URL);

                if (!response.ok) {
                    throw new Error(`Erro na API. Status: ${response.status}`);
                }

                const data = await response.json();
                setVendas(data);

            } catch (err) {
                console.error("Erro ao carregar vendas:", err);
                setError("Não foi possível carregar as vendas do Backend. Verifique o servidor.");
            } finally {
                setLoading(false);
            }
        };

        fetchVendas();
    }, []);

    if (loading) return <div style={{padding: '10px'}}>Carregando transações de venda...</div>;
    if (error) return <div style={{color: 'red', padding: '10px'}}>{error}</div>;

    return (
        <div style={{border: '1px solid #ccc', padding: '15px', margin: '20px'}}>
            <h2>Transações de Venda ({vendas.length} Registros)</h2>
            {vendas.length === 0 ? (
                <p>Nenhuma venda registrada até o momento.</p>
            ) : (
                <ul style={{listStyle: 'none', padding: 0}}>
                    {vendas.map(venda => (
                        <li key={venda.id} style={{marginBottom: '10px', borderBottom: '1px dotted #ccc', paddingBottom: '5px'}}>
                            <strong>ID Venda:</strong> {venda.id} |
                            <strong> Valor:</strong> R$ {venda.valorFinal ? venda.valorFinal.toFixed(2) : 'N/D'} |
                            <strong> Cliente:</strong> ID {venda.clienteComprador.id} |
                            <strong> Veículo:</strong> ID {venda.veiculoVendido.id} |
                            <strong> Data:</strong> {venda.dataVenda}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default VendaList;