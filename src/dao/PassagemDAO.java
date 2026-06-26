package dao;

import connection.ConnectionDB;
import model.Passagem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// classe responsável por executar as operações de banco de dados para Passagem
public class PassagemDAO {

    // registra uma nova passagem no banco de dados
    public void inserir(Passagem p) {
        String sql = "INSERT INTO Passagem (numero_assento, data_compra, status, id_cliente, id_viagem) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // preenche os parâmetros com os dados da passagem
            ps.setInt(1, p.getNumeroAssento());
            ps.setString(2, p.getDataCompra());
            ps.setString(3, p.getStatus());
            ps.setInt(4, p.getIdCliente());
            ps.setInt(5, p.getIdViagem());
            ps.executeUpdate();
            System.out.println("Passagem inserida com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir passagem: " + e.getMessage());
        }
    }

    // retorna todas as passagens cadastradas no banco
    public List<Passagem> listar() {
        List<Passagem> lista = new ArrayList<>();
        String sql = "SELECT * FROM Passagem";
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // percorre cada linha e cria um objeto Passagem
            while (rs.next()) {
                Passagem p = new Passagem(
                        rs.getInt("id_passagem"),
                        rs.getInt("numero_assento"),
                        rs.getString("data_compra"),
                        rs.getString("status"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_viagem")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar passagens: " + e.getMessage());
        }
        return lista;
    }

    // busca uma passagem específica pelo id
    public Passagem buscarPorId(int id) {
        String sql = "SELECT * FROM Passagem WHERE id_passagem = ?";
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            // se encontrou a passagem, retorna ela
            if (rs.next()) {
                return new Passagem(
                        rs.getInt("id_passagem"),
                        rs.getInt("numero_assento"),
                        rs.getString("data_compra"),
                        rs.getString("status"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_viagem")
                );
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar passagem: " + e.getMessage());
        }
        return null;
    }

    // atualiza os dados de uma passagem existente
    public void atualizar(Passagem p) {
        String sql = "UPDATE Passagem SET numero_assento = ?, status = ?," +
                " id_cliente = ?, id_viagem = ? WHERE id_passagem = ?";
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getNumeroAssento());
            ps.setString(2, p.getStatus());
            ps.setInt(3, p.getIdCliente());
            ps.setInt(4, p.getIdViagem());
            ps.setInt(5, p.getIdPassagem());
            ps.executeUpdate();
            System.out.println("Passagem atualizada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar passagem: " + e.getMessage());
        }
    }

    // remove uma passagem do banco pelo id
    public void deletar(int id) {
        String sql = "DELETE FROM Passagem WHERE id_passagem = ?";
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Passagem removida com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao remover passagem: " + e.getMessage());
        }
    }
}