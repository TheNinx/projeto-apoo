package br.edu.facthus.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.facthus.exception.CustomException;
import br.edu.facthus.model.Corretor;
import br.edu.facthus.model.Pessoa;

//Tarefa 12: completar...
public class CorretorDAO {

	private Connection connection = null;

	private Connection getConnection() {
		try {
			if (connection == null)
				connection = DriverManager
						.getConnection("jdbc:sqlite:imoveis.db");

			return connection;
		} catch (SQLException e) {
			throw new CustomException("Erro abrindo conexão com o banco de dados.");
		}
	}



	public void criaBD() {
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();

			statement.executeUpdate(
					"CREATE TABLE IF NOT EXISTS contatos ("
							+ "ID INTEGER,"
							+ "nome TEXT,"
							+ "CPF TEXT"
							+ "Telefone TEXT"
							+ "Cresci TEXT"
							+ "Corretagem DOUBLE"
							+ "Email TEXT,"
							+ "PRIMARY KEY (id))");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException("Ocorreu um erro ao criar o banco de dados.");
		}
	}

	public void insere(Corretor corretor) {
		try {
			Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO contatos (Id,nome, cpf,telefone,cresci,corretagem,email) "
							+ "VALUES (?, ?, ? , ?, ?,?,?)");


			statement.setString(2, corretor.getNome());
			statement.setString(3, corretor.getCpf());
			statement.setString(4, corretor.getTelefone());
			statement.setString(5, corretor.getCresci());
			statement.setDouble(6, corretor.getCorretagem());



			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException("Ocorreu um erro ao inserir o contato.");
		}
	}

	public List<Corretor> pesquisa() {
		List<Corretor> corretores = new ArrayList<>();

		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(
					"SELECT id, nome, email FROM contatos ORDER BY email");

			while (rs.next()) {
				Corretor corretor = new Corretor(rs.getString("nome"),rs.getString("cpf"),rs.getString("Telefone"),rs.getString("Cresci: "),rs.getDouble("Corretagem:"));




				corretores.add(corretor);
			}

			return corretores;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException("Ocorreu um erro ao pesquisar os contatos.");
		}

	}

}
