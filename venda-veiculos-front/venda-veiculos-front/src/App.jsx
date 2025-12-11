import React, { useState } from 'react';


import VendedorForm from './components/Vendedor/VendedorForm';
import VeiculoForm from './components/Veiculo/VeiculoForm';
import VendaForm from './components/Venda/VendaForm';
import ClienteForm from './components/Cliente/ClienteForm';

import VendedorList from './components/Vendedor/VendedorList';
import VeiculoList from './components/Veiculo/VeiculoList';
import VendaList from './components/Venda/VendaList';
import ClienteList from './components/Cliente/ClienteList';


function App() {


  const [activeTab, setActiveTab] = useState('Vendas');

  const [vendedorKey, setVendedorKey] = useState(0);
  const [veiculoKey, setVeiculoKey] = useState(0);
  const [vendaKey, setVendaKey] = useState(0);
  const [clienteKey, setClienteKey] = useState(0);


  const handleVendedorCadastrado = () => { setVendedorKey(prevKey => prevKey + 1); };
  const handleVeiculoCadastrado = () => { setVeiculoKey(prevKey => prevKey + 1); };
  const handleVendaRegistrada = () => { setVendaKey(prevKey => prevKey + 1); };
  const handleClienteCadastrado = () => { setClienteKey(prevKey => prevKey + 1); };

  const renderContent = () => {
    switch (activeTab) {
      case 'Vendas':
        return (

          <div className="tab-content-grid">

            {/* Coluna de Cadastro de Venda */}
            <div className="column-block">
              <div className="component-block">
                <h3 style={{color: '#E91E63'}}>Registrar Nova Venda</h3>
                <VendaForm onCadastroSucesso={handleVendaRegistrada} />
              </div>
            </div>

            {/* Coluna de Listagem de Vendas */}
            <div className="column-block">
              <div className="component-block">
                <h3 style={{color: '#E91E63'}}>Transações de Venda</h3>
                <VendaList key={vendaKey} />
              </div>
            </div>
          </div>
        );

      case 'Clientes':
        return (

          <div className="tab-content-grid">

            {/* Coluna de Cadastro de Cliente */}
            <div className="column-block">
              <div className="component-block">
                <h3 style={{color: '#2196F3'}}>Cadastrar Novo Cliente</h3>
                <ClienteForm onCadastroSucesso={handleClienteCadastrado} />
              </div>
            </div>

            {/* Coluna de Listagem de Clientes */}
            <div className="column-block">
              <div className="component-block">
                <h3 style={{color: '#2196F3'}}>Clientes Cadastrados</h3>
                <ClienteList key={clienteKey} />
              </div>
            </div>
          </div>
        );

      case 'Estoque':
        return (

          <div className="tab-content-grid">

            {/* Coluna de Cadastro de Veículo */}
            <div className="column-block">
              <div className="component-block">
                <h3 style={{color: '#FF9800'}}>Cadastrar Novo Veículo</h3>
                <VeiculoForm onCadastroSucesso={handleVeiculoCadastrado} />
              </div>
            </div>

            {/* Coluna de Listagem de Veículos */}
            <div className="column-block">
              <div className="component-block">
                <h3 style={{color: '#FF9800'}}>Estoque de Veículos</h3>
                <VeiculoList key={veiculoKey} />
              </div>
            </div>
          </div>
        );

      case 'Vendedores':
        return (

          <div className="tab-content-grid">

            {/* Coluna de Cadastro de Vendedor */}
            <div className="column-block">
              <div className="component-block">
                <h3 style={{color: '#8BC34A'}}>Cadastrar Novo Vendedor</h3>
                <VendedorForm onCadastroSucesso={handleVendedorCadastrado} />
              </div>
            </div>

            {/* Coluna de Listagem de Vendedores */}
            <div className="column-block">
              <div className="component-block">
                <h3 style={{color: '#8BC34A'}}>Vendedores Cadastrados</h3>
                <VendedorList key={vendedorKey} />
              </div>
            </div>
          </div>
        );

      default:
        return <div>Selecione uma aba.</div>;
    }
  };


  return (
    <>
      <h1 style={{textAlign: 'center', color: '#4CAF50', padding: '20px', borderBottom: '2px solid #333', marginBottom: '20px'}}>
        ----Sistema de Venda de Veículos <small style={{fontSize: '0.5em', color: '#888'}}> ----- </small>
      </h1>


      <div className="tabs-menu">
        <button
          className={activeTab === 'Vendas' ? 'tab-active' : 'tab-inactive'}
          onClick={() => setActiveTab('Vendas')}>
          💰 Vendas
        </button>
        <button
          className={activeTab === 'Clientes' ? 'tab-active' : 'tab-inactive'}
          onClick={() => setActiveTab('Clientes')}>
          👥 Clientes
        </button>
        <button
          className={activeTab === 'Estoque' ? 'tab-active' : 'tab-inactive'}
          onClick={() => setActiveTab('Estoque')}>
          🚗 Estoque
        </button>
        <button
          className={activeTab === 'Vendedores' ? 'tab-active' : 'tab-inactive'}
          onClick={() => setActiveTab('Vendedores')}>
          🧑‍💼 Vendedores
        </button>
      </div>


      <div className="dashboard-area">
        {renderContent()}
      </div>
    </>
  );
}

export default App;