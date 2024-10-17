package com.example.ms_gerenciador_.cadastros.model.enums;

public enum StatusDrone {
    DISPONIVEL,
    EM_ROTA,
    EM_MANUTENCAO,
    DESATIVADO;

    public static boolean equals(String status) {
        if (status.equalsIgnoreCase("DISPONIVEL")
                || status.equalsIgnoreCase("DESATIVADO")
                || status.equalsIgnoreCase("EM_MANUTENCAO")
                || status.equalsIgnoreCase("EM_ROTA")) {
            return true;
        }
        return false;
    }
}
