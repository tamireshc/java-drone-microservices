package com.example.ms_gerenciador_pedidos.model.enuns;

public enum StatusPedido {
    CRIADO,
    EM_ROTA,
    ENTREGUE,
    CANCELADO;

    public static boolean equals(String status) {
        if (status.equalsIgnoreCase("CRIADO")
                || status.equalsIgnoreCase("EM_ROTA")
                || status.equalsIgnoreCase("ENTREGUE")
                || status.equalsIgnoreCase("CANCELADO")) {
            return true;
        }
        return false;
    }
}
