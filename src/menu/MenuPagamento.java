package menu;

import dao.PagamentoDAO;
import model.Pagamento;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// menu de opções para gerenciar pagamentos no console
public class MenuPagamento {

    private final PagamentoDAO dao = new PagamentoDAO();
    private final Scanner sc;

    public MenuPagamento(Scanner sc) {
        this.sc = sc;
    }

    public void exibir() {
        int opcao = -1;
        do {
            System.out.println("\n===== MENU PAGAMENTO =====");
            System.out.println("1. Registrar pagamento");
            System.out.println("2. Listar pagamentos");
            System.out.println("3. Buscar pagamento por ID");
            System.out.println("4. Atualizar pagamento");
            System.out.println("5. Remover pagamento");
            System.out.println("0. Voltar");
            System.out.print("Opcao: ");

            // tratativa de erro caso o usuário digite algo que não seja número
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
        System.out.println("\n--- Registrar Pagamento ---");
        try {
            System.out.print("Valor: ");
            double valor = sc.nextDouble();
            sc.nextLine();
            System.out.print("Forma de pagamento (pix/cartao_credito/cartao_debito/dinheiro): ");
            String forma = sc.nextLine();
            System.out.print("Status (pendente/aprovado/recusado/estornado): ");
            String status = sc.nextLine();
            System.out.print("Data do pagamento (AAAA-MM-DD HH:MM:SS): ");
            String data = sc.nextLine();
            System.out.print("ID da passagem: ");
            int idPassagem = sc.nextInt();
            sc.nextLine();

            Pagamento p = new Pagamento(0, valor, forma, status, data, idPassagem);
            dao.inserir(p);
        } catch (InputMismatchException e) {
            System.out.println("Valor invalido! Verifique os campos numericos.");
            sc.nextLine();
        }
        pausar();
    }

    private void listar() {
        System.out.println("\n--- Lista de Pagamentos ---");
        List<Pagamento> lista = dao.listar();
        if (lista.isEmpty()) {
            System.out.println("Nenhum pagamento encontrado.");
        } else {
            lista.forEach(System.out::println);
        }
        pausar();
    }

    private void buscar() {
        System.out.print("\nID do pagamento: ");
        try {
            int id = sc.nextInt();
            sc.nextLine();
            Pagamento p = dao.buscarPorId(id);
            if (p != null) {
                System.out.println(p);
            } else {
                System.out.println("Pagamento nao encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("ID invalido! Digite apenas numeros.");
            sc.nextLine();
        }
        pausar();
    }

    private void atualizar() {
        System.out.print("\nID do pagamento a atualizar: ");
        try {
            int id = sc.nextInt();
            sc.nextLine();
            Pagamento p = dao.buscarPorId(id);
            if (p == null) {
                System.out.println("Pagamento nao encontrado.");
                pausar();
                return;
            }
            System.out.print("Novo status (" + p.getStatus() + "): ");
            String status = sc.nextLine();
            if (!status.isEmpty()) p.setStatus(status);
            dao.atualizar(p);
        } catch (InputMismatchException e) {
            System.out.println("ID invalido! Digite apenas numeros.");
            sc.nextLine();
        }
        pausar();
    }

    private void remover() {
        System.out.print("\nID do pagamento a remover: ");
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