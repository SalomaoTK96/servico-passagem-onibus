package menu;

import dao.ViagemDAO;
import model.Viagem;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// menu de opções para gerenciar viagens no console
public class MenuViagem {

    private final ViagemDAO dao = new ViagemDAO();
    private final Scanner sc;

    public MenuViagem(Scanner sc) {
        this.sc = sc;
    }

    public void exibir() {
        int opcao = -1;
        do {
            System.out.println("\n===== MENU VIAGEM =====");
            System.out.println("1. Cadastrar viagem");
            System.out.println("2. Listar viagens");
            System.out.println("3. Buscar viagem por ID");
            System.out.println("4. Atualizar viagem");
            System.out.println("5. Remover viagem");
            System.out.println("0. Voltar");
            System.out.print("Opcao: ");

            // tratativa de erro caso o seja digite algo que não seja número
            try {
                opcao = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Opcao invalida! Digite apenas numeros.");
                sc.nextLine();
                continue;
            }

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 3 -> buscar();
                case 4 -> atualizar();
                case 5 -> remover();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);
    }

    // aguarda o usuário pressionar Enter para continuar
    private void pausar() {
        System.out.print("\nPressione Enter para continuar...");
        sc.nextLine();
    }

    private void cadastrar() {
        System.out.println("\n--- Cadastrar Viagem ---");
        try {
            System.out.print("Data de partida (AAAA-MM-DD HH:MM:SS): ");
            String partida = sc.nextLine();
            System.out.print("Data de chegada (AAAA-MM-DD HH:MM:SS): ");
            String chegada = sc.nextLine();
            System.out.print("Status (agendada/em_andamento/concluida/cancelada): ");
            String status = sc.nextLine();
            System.out.print("ID do onibus: ");
            int idOnibus = sc.nextInt();
            System.out.print("ID da rota: ");
            int idRota = sc.nextInt();
            System.out.print("ID do motorista: ");
            int idMotorista = sc.nextInt();
            sc.nextLine();

            Viagem v = new Viagem(0, partida, chegada, status, idOnibus, idRota, idMotorista);
            dao.inserir(v);
        } catch (InputMismatchException e) {
            System.out.println("Valor invalido! Digite apenas numeros nos campos de ID.");
            sc.nextLine();
        }
        pausar();
    }

    private void listar() {
        System.out.println("\n--- Lista de Viagens ---");
        List<Viagem> lista = dao.listar();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma viagem cadastrada.");
        } else {
            lista.forEach(System.out::println);
        }
        pausar();
    }

    private void buscar() {
        System.out.print("\nID da viagem: ");
        try {
            int id = sc.nextInt();
            sc.nextLine();
            Viagem v = dao.buscarPorId(id);
            if (v != null) {
                System.out.println(v);
            } else {
                System.out.println("Viagem nao encontrada.");
            }
        } catch (InputMismatchException e) {
            System.out.println("ID invalido! Digite apenas numeros.");
            sc.nextLine();
        }
        pausar();
    }

    private void atualizar() {
        System.out.print("\nID da viagem a atualizar: ");
        try {
            int id = sc.nextInt();
            sc.nextLine();
            Viagem v = dao.buscarPorId(id);
            if (v == null) {
                System.out.println("Viagem nao encontrada.");
                pausar();
                return;
            }
            System.out.print("Novo status (" + v.getStatus() + "): ");
            String status = sc.nextLine();
            if (!status.isEmpty()) v.setStatus(status);
            dao.atualizar(v);
        } catch (InputMismatchException e) {
            System.out.println("ID invalido! Digite apenas numeros.");
            sc.nextLine();
        }
        pausar();
    }

    private void remover() {
        System.out.print("\nID da viagem a remover: ");
        try {
            int id = sc.nextInt();
            sc.nextLine();
            dao.deletar(id);
        } catch (InputMismatchException e) {
            System.out.println("ID invalido! Digite apenas numeros.");
            sc.nextLine();
        }
        pausar();
    }
}