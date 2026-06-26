package dao;

import connection.ConnectionDB;
import model.Pagamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// classe responsável por executar as operações de banco de dados para Pagamento
public class PagamentoDAO {

    // registra um novo pagamento no banco de dados
    public void inserir(Pagamento p) {
        String sql = "INSERT INTO Pagamento (valor, forma_pagamento, status, data_pagamento, id_passagem) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // preenche os parâmetros com os dados do pagamento
            ps.setDouble(1, p.getValor());
            ps.setString(2, p.getFormaPagamento());
            ps.setString(3, p.getStatus());
            ps.setString(4, p.getDataPagamento());
            ps.setInt(5, p.getIdPassagem());
            ps.executeUpdate();
            System.out.println("Pagamento inserido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir pagamento: " + e.getMessage());
        }
    }

    // retorna todos os pagamentos cadastrados no banco
    public List<Pagamento> listar() {
        List<Pagamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pagamento";
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // percorre cada linha e cria um objeto Pagamento
            while (rs.next()) {
                Pagamento p = new Pagamento(
                        rs.getInt("id_pagamento"),
                        rs.getDouble("valor"),
                        rs.getString("forma_pagamento"),
                        rs.getString("status"),
                        rs.getString("data_pagamento"),
                        rs.getInt("id_passagem")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar pagamentos: " + e.getMessage());
        }
        return lista;
    }

    // busca um pagamento específico pelo id
    public Pagamento buscarPorId(int id) {
        String sql = "SELECT * FROM Pagamento WHERE id_pagamento = ?";
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            // se encontrou o pagamento, retorna ele
            if (rs.next()) {
                return new Pagamento(
                        rs.getInt("id_pagamento"),
                        rs.getDouble("valor"),
                        rs.getString("forma_pagamento"),
                        rs.getString("status"),
                        rs.getString("data_pagamento"),
                        rs.getInt("id_passagem")
                );
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar pagamento: " + e.getMessage());
        }
        return null;
    }

    // atualiza os dados de um pagamento existente
    public void atualizar(Pagamento p) {
        String sql = "UPDATE Pagamento SET valor = ?, forma_pagamento = ?, status = ?," +
                " data_pagamento = ?, id_passagem = ? WHERE id_pagamento = ?";
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, p.getValor());
            ps.setString(2, p.getFormaPagamento());
            ps.setString(3, p.getStatus());
            ps.setString(4, p.getDataPagamento());
            ps.setInt(5, p.getIdPassagem());
            ps.setInt(6, p.getIdPagamento());
            ps.executeUpdate();
            System.out.println("Pagamento atualizado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar pagamento: " + e.getMessage());
        }
    }

    // remove um pagamento do banco pelo id
    public void deletar(int id) {
        String sql = "DELETE FROM Pagamento WHERE id_pagamento = ?";
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Pagamento removido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao remover pagamento: " + e.getMessage());
        }
    }
}