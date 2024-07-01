import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {
    private DatabaseConnection databaseConnection;

    public ClienteDAO() throws SQLException {
        this.databaseConnection = DatabaseConnection.getInstance();
    }

    public void agregarCliente(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO Clientes (nombre, direccion) VALUES (?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getDireccion());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al agregar el cliente", e);
        } finally {
            // Cierra la conexión cuando termines de usarla
            databaseConnection.closeConnection();
        }
    }

    public Cliente obtenerCliente(int idCliente) throws SQLException {
        String sql = "SELECT * FROM Clientes WHERE idCliente = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idCliente);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String nombre = resultSet.getString("nombre");
                    String direccion = resultSet.getString("direccion");
                    return new Cliente(idCliente, nombre, direccion);
                } else {
                    return null; // Cliente no encontrado
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al obtener el cliente", e);
        } finally {
            // Cierra la conexión cuando termines de usarla
            databaseConnection.closeConnection();
        }
    }

}