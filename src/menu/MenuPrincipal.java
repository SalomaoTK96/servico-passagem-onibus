package menu;

import java.util.InputMismatchException;
import java.util.Scanner;

// menu principal que direciona para os submenus do sistema
public class MenuPrincipal {

    private final Scanner sc = new Scanner(System.in);

    public void exibir() {
        int opcao = -1;
        do {
            System.out.println("\n========================================");
            System.out.println("  SISTEMA DE PASSAGENS - CATARINENSE");
            System.out.println("========================================");
            System.out.println("1. Clientes");
            System.out.println("2. Viagens");
            System.out.println("3. Passagens");
            System.out.println("4. Pagamentos");
            System.out.println("0. Sair");
            System.out.println("========================================");
            System.out.print("Opcao: ");

            // tratativa de erro caso o seja digite algo que não seja número
            try {
                opcao = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Opcao invalida! Digite apenas numeros.");
                sc.nextLine(); // limpa o buffer do scanner
                continue;
            }

            // direciona para o menu correspondente
            switch (opcao) {
                case 1 -> new MenuCliente(sc).exibir();
                case 2 -> new MenuViagem(sc).exibir();
                case 3 -> new MenuPassagem(sc).exibir();
                case 4 -> new MenuPagamento(sc).exibir();
                case 0 -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);

        sc.close();
    }
}