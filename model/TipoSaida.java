package model;

/**
 * Enum que define os tipos de saída de um produto do estoque.
 * Cada valor representa um tipo específico de saída, como venda, uso interno, devolução ao fornecedor, ou outro tipo não especificado.
 */
public enum TipoSaida {

    /**
     * Representa a saída do produto devido a uma venda para o cliente.
     */
    vendaCliente,

    /**
     * Representa a saída do produto para uso interno da empresa.
     */
    usoInterno,

    /**
     * Representa a devolução de um produto ao fornecedor.
     */
    devolucaoFornecedor,

    /**
     * Representa outro tipo de saída não especificado.
     */
    Outra
}
