import React, { useState } from 'react';
import axios from 'axios';

const ConcurrentAPICalls = () => {
  const [results, setResults] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  const createItems = async (prefix) => {
    try {
      const stockItems = await import(`./${prefix}_stock_items.json`);
      const requests = stockItems.map(item => {
        const url = `http://localhost:8080/${prefix}/sock/item`;
        return axios.post(url, item);
      });

      const responses = await Promise.all(requests);

      const responseData = responses.map(response => response.data);
      setResults(prevResults => [...prevResults, { [prefix]: responseData }]);

      // Atualiza aleatoriamente a quantidade de alguns itens
      const randomItemIds = Array.from({ length: stockItems.length }, (_, index) => index + 1);
      const randomItemId = randomItemIds[Math.floor(Math.random() * randomItemIds.length)];
      const randomIncrementAmount = Math.floor(Math.random() * 10) + 1; // Incrementa de 1 a 10

      const updateUrl = `http://localhost:8080/${prefix}/sock/item/${randomItemId}/update`;
      await axios.post(updateUrl, { incrementAmount: randomIncrementAmount });

    } catch (error) {
      console.error(`Error during API calls for ${prefix}:`, error);
    }
  };

  return (
    <div>
      <h1>Concurrent API Calls</h1>
      <button onClick={() => createItems('optimistic')} disabled={isLoading}>
        {isLoading ? 'Carregando...' : 'Criar/Atualizar Itens de Estoque (Otimista)'}
      </button>
      <button onClick={() => createItems('pessimistic')} disabled={isLoading}>
        {isLoading ? 'Carregando...' : 'Criar/Atualizar Itens de Estoque (Pessimista)'}
      </button>
      <ul>
        {results.map((data, index) => (
          <li key={index}>
            {Object.entries(data).map(([prefix, responseData], index) => (
              <div key={index}>
                <strong>{prefix}:</strong>
                <ul>
                  {responseData.map((item, index) => (
                    <li key={index}>{item}</li>
                  ))}
                </ul>
              </div>
            ))}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ConcurrentAPICalls;
