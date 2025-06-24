package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import console.Console;
import usuario.SexoPessoa;
import usuario.Usuario;
import usuario.gerenciar.GerenciarUsuario;

public class ProgramaPrincipal {

	private GerenciarUsuario gerenciarUsuario;
	private Console console;

	private static final int CADASTRAR = 1;
	private static final int ALTERAR = 2;
	private static final int LISTAR = 3;
	private static final int REMOVER = 4;
	private static final int PESQUISAR_POR_NOME = 5;
	private static final int SAIR = 9;

	public ProgramaPrincipal() {
		gerenciarUsuario = new GerenciarUsuario();
		console = new Console();
	}

	public static void main(String[] args) {
		new ProgramaPrincipal().executar();
	}

	private void executar() {
		int opcao = 0;

		do {
			imprimirMenu();

			opcao = console.readInt("Digite uma opção: ");

			if (opcao == CADASTRAR) {
				cadastrar();
			} else if (opcao == ALTERAR) {
				alterar();
			} else if (opcao == LISTAR) {
				listar();
			} else if (opcao == REMOVER) {
				remover();
			} else if (opcao == PESQUISAR_POR_NOME) {
				pesquisarPorNome();
			}

		} while (opcao != SAIR);

		gerenciarUsuario.fechar();
		System.out.println("Terminando o programa, bye");
	}

	private void pesquisarPorNome() {
		System.out.println("--- Pesquisar usuário por nome ---");

		String nome = console.readLine("Digite o nome a pesquisar: ");
		List<Usuario> usuarios = gerenciarUsuario.pesquisarPorNome(nome);

		if (usuarios.isEmpty()) {
			System.out.println("Nenhum usuário encontrado com esse nome.");
		} else {
			imprimirLista(usuarios);
		}
	}

	private void remover() {
		System.out.println("--- Remover usuário ---");

		int idParaExcluir = console.readInt("Digite o ID para excluir: ");
		gerenciarUsuario.remover(idParaExcluir);
		System.out.println("Usuário excluído com sucesso");
	}

	private void listar() {
		System.out.println("--- Listar usuários ---");

		List<Usuario> usuarios = gerenciarUsuario.listar();
		imprimirLista(usuarios);
	}

	private void imprimirLista(List<Usuario> usuarios) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		for (Usuario usuario : usuarios) {
			System.out.println("ID: " + usuario.getId());
			System.out.println("Nome: " + usuario.getNome());
			System.out.println("Email: " + usuario.getEmail());
			System.out.println("Sexo: " + usuario.getSexo());
			System.out.println("Data de nascimento: " + dtf.format(usuario.getDataNascimento()));
		}
	}

	private void alterar() {
		System.out.println("\nAlterar usuário\n");

		int idParaAlterar = console.readInt("Digite o ID para alterar: ");

		Usuario usuario = gerenciarUsuario.findById(idParaAlterar);
		if (usuario == null) {
			System.out.println("Usuário não encontrado.");
			return;
		}

		lerDadosUsuario(usuario);
		gerenciarUsuario.atualizar(usuario);

		System.out.println("Usuário alterado com sucesso");
	}

	private void cadastrar() {
		System.out.println("Cadastrar novo usuário");

		Usuario usuario = new Usuario();
		lerDadosUsuario(usuario);

		gerenciarUsuario.salvar(usuario);

		System.out.println("Usuário criado com sucesso");
	}

	private void lerDadosUsuario(Usuario usuario) {
		String nome = console.readLine("Digite o nome: ");
		String email = console.readLine("Digite o email: ");

		SexoPessoa sexo = null;
		while (sexo == null) {
			String sexoStr = console.readLine("Digite o sexo (M/F): ").toUpperCase();
			if (sexoStr.equals("M")) {
				sexo = SexoPessoa.MASCULINO;
			} else if (sexoStr.equals("F")) {
				sexo = SexoPessoa.FEMININO;
			} else {
				sexo = SexoPessoa.NAO_INFORMADO;
			}
		}

		LocalDate dataNascimento = console.readLocalDate("Digite a data de nascimento (dd/MM/yyyy): ");

		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSexo(sexo);
		usuario.setDataNascimento(dataNascimento);
	}

	private void imprimirMenu() {
		System.out.println("");
		System.out.println("--- SUPER CRUD ---");
		System.out.println("");
		System.out.println("1 - Cadastrar usuário");
		System.out.println("2 - Alterar usuário");
		System.out.println("3 - Listar usuários");
		System.out.println("4 - Remover usuário");
		System.out.println("5 - Pesquisar usuário por nome");
		System.out.println("9 - Sair");
	}
}
