package org.freeuni.demo.server.database.managers;

import org.freeuni.demo.server.database.models.Creator;
import org.freeuni.demo.server.database.models.Post;
import org.freeuni.demo.server.database.models.User;
import org.freeuni.demo.server.database.source.SQLConnectionFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostManagerSQLTest {
    PostManagerSQL postManager;
    PreparedStatementMock prepStatement;
    ConnectionFactoryMock connectionFactory;
    ConnectionMock connection;
    CreatorMock creator;

    @Before
    public void setUp()  {

        prepStatement = new PreparedStatementMock();
        connectionFactory = new ConnectionFactoryMock();
        connection = new ConnectionMock();
        creator = new CreatorMock();

        postManager = new PostManagerSQL(connectionFactory, creator);
    }

    @Test
    public void addPost() throws SQLException {
        Long postId = 12L;
        Long userId = 11L;
        String content = "text";

        User user = mock(User.class);
        Post post = mock(Post.class);

        when(post.getId()).thenReturn(postId);
        when(post.getAuthor()).thenReturn(user);
        when(user.getId()).thenReturn(userId);
        when(post.getContent()).thenReturn(content);

        postManager.addPost(post);

        Long actualPostId = (Long)prepStatement.row[1];
        Long actualUserId = (Long)prepStatement.row[2];
        String actualContent = (String)prepStatement.row[3];
        assertEquals(actualPostId, postId);
        assertEquals(actualUserId, userId);
        assertEquals(actualContent, content);
    }

    @Test
    public void byUserId() {

    }

    class PreparedStatementMock extends PreparedStatementMockAdapter {
        public Object[] row = new Object[4];

        @Override
        public void setLong(int idx, long value) {
            row[idx] = value;
        }

        @Override
        public void setString(int idx, String str) {
            row[idx] = str;
        }
    }

    class CreatorMock implements Creator {

        @Override
        public Post createPost(long user_id, String username, long post_id, String content) {
            return null;
        }

        @Override
        public List<Post> postsFromResultSet(ResultSet rs) {
            return null;
        }
    }
    class ConnectionMock extends ConnectionMockAdapter {
        @Override
        public PreparedStatement prepareStatement(String sql) {
            return prepStatement;
        }
    }
    class PreparedStatementMockAdapter implements PreparedStatement {
        @Override
        public ResultSet executeQuery() throws SQLException {
            return null;
        }

        @Override
        public int executeUpdate() throws SQLException {
            return 0;
        }

        @Override
        public void setNull(int parameterIndex, int sqlType) throws SQLException {

        }

        @Override
        public void setBoolean(int parameterIndex, boolean x) throws SQLException {

        }

        @Override
        public void setByte(int parameterIndex, byte x) throws SQLException {

        }

        @Override
        public void setShort(int parameterIndex, short x) throws SQLException {

        }

        @Override
        public void setInt(int parameterIndex, int x) throws SQLException {

        }

        @Override
        public void setLong(int parameterIndex, long x) throws SQLException {

        }

        @Override
        public void setFloat(int parameterIndex, float x) throws SQLException {

        }

        @Override
        public void setDouble(int parameterIndex, double x) throws SQLException {

        }

        @Override
        public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {

        }

        @Override
        public void setString(int parameterIndex, String x) throws SQLException {

        }

        @Override
        public void setBytes(int parameterIndex, byte[] x) throws SQLException {

        }

        @Override
        public void setDate(int parameterIndex, Date x) throws SQLException {

        }

        @Override
        public void setTime(int parameterIndex, Time x) throws SQLException {

        }

        @Override
        public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {

        }

        @Override
        public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {

        }

        @Override
        public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {

        }

        @Override
        public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {

        }

        @Override
        public void clearParameters() throws SQLException {

        }

        @Override
        public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {

        }

        @Override
        public void setObject(int parameterIndex, Object x) throws SQLException {

        }

        @Override
        public boolean execute() throws SQLException {
            return false;
        }

        @Override
        public void addBatch() throws SQLException {

        }

        @Override
        public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {

        }

        @Override
        public void setRef(int parameterIndex, Ref x) throws SQLException {

        }

        @Override
        public void setBlob(int parameterIndex, Blob x) throws SQLException {

        }

        @Override
        public void setClob(int parameterIndex, Clob x) throws SQLException {

        }

        @Override
        public void setArray(int parameterIndex, Array x) throws SQLException {

        }

        @Override
        public ResultSetMetaData getMetaData() throws SQLException {
            return null;
        }

        @Override
        public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {

        }

        @Override
        public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {

        }

        @Override
        public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {

        }

        @Override
        public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {

        }

        @Override
        public void setURL(int parameterIndex, URL x) throws SQLException {

        }

        @Override
        public ParameterMetaData getParameterMetaData() throws SQLException {
            return null;
        }

        @Override
        public void setRowId(int parameterIndex, RowId x) throws SQLException {

        }

        @Override
        public void setNString(int parameterIndex, String value) throws SQLException {

        }

        @Override
        public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {

        }

        @Override
        public void setNClob(int parameterIndex, NClob value) throws SQLException {

        }

        @Override
        public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {

        }

        @Override
        public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {

        }

        @Override
        public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {

        }

        @Override
        public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {

        }

        @Override
        public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {

        }

        @Override
        public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {

        }

        @Override
        public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {

        }

        @Override
        public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {

        }

        @Override
        public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {

        }

        @Override
        public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {

        }

        @Override
        public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {

        }

        @Override
        public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {

        }

        @Override
        public void setClob(int parameterIndex, Reader reader) throws SQLException {

        }

        @Override
        public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {

        }

        @Override
        public void setNClob(int parameterIndex, Reader reader) throws SQLException {

        }

        @Override
        public ResultSet executeQuery(String sql) throws SQLException {
            return null;
        }

        @Override
        public int executeUpdate(String sql) throws SQLException {
            return 0;
        }

        @Override
        public void close() throws SQLException {

        }

        @Override
        public int getMaxFieldSize() throws SQLException {
            return 0;
        }

        @Override
        public void setMaxFieldSize(int max) throws SQLException {

        }

        @Override
        public int getMaxRows() throws SQLException {
            return 0;
        }

        @Override
        public void setMaxRows(int max) throws SQLException {

        }

        @Override
        public void setEscapeProcessing(boolean enable) throws SQLException {

        }

        @Override
        public int getQueryTimeout() throws SQLException {
            return 0;
        }

        @Override
        public void setQueryTimeout(int seconds) throws SQLException {

        }

        @Override
        public void cancel() throws SQLException {

        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return null;
        }

        @Override
        public void clearWarnings() throws SQLException {

        }

        @Override
        public void setCursorName(String name) throws SQLException {

        }

        @Override
        public boolean execute(String sql) throws SQLException {
            return false;
        }

        @Override
        public ResultSet getResultSet() throws SQLException {
            return null;
        }

        @Override
        public int getUpdateCount() throws SQLException {
            return 0;
        }

        @Override
        public boolean getMoreResults() throws SQLException {
            return false;
        }

        @Override
        public void setFetchDirection(int direction) throws SQLException {

        }

        @Override
        public int getFetchDirection() throws SQLException {
            return 0;
        }

        @Override
        public void setFetchSize(int rows) throws SQLException {

        }

        @Override
        public int getFetchSize() throws SQLException {
            return 0;
        }

        @Override
        public int getResultSetConcurrency() throws SQLException {
            return 0;
        }

        @Override
        public int getResultSetType() throws SQLException {
            return 0;
        }

        @Override
        public void addBatch(String sql) throws SQLException {

        }

        @Override
        public void clearBatch() throws SQLException {

        }

        @Override
        public int[] executeBatch() throws SQLException {
            return new int[0];
        }

        @Override
        public Connection getConnection() throws SQLException {
            return null;
        }

        @Override
        public boolean getMoreResults(int current) throws SQLException {
            return false;
        }

        @Override
        public ResultSet getGeneratedKeys() throws SQLException {
            return null;
        }

        @Override
        public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
            return 0;
        }

        @Override
        public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
            return 0;
        }

        @Override
        public int executeUpdate(String sql, String[] columnNames) throws SQLException {
            return 0;
        }

        @Override
        public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
            return false;
        }

        @Override
        public boolean execute(String sql, int[] columnIndexes) throws SQLException {
            return false;
        }

        @Override
        public boolean execute(String sql, String[] columnNames) throws SQLException {
            return false;
        }

        @Override
        public int getResultSetHoldability() throws SQLException {
            return 0;
        }

        @Override
        public boolean isClosed() throws SQLException {
            return false;
        }

        @Override
        public void setPoolable(boolean poolable) throws SQLException {

        }

        @Override
        public boolean isPoolable() throws SQLException {
            return false;
        }

        @Override
        public void closeOnCompletion() throws SQLException {

        }

        @Override
        public boolean isCloseOnCompletion() throws SQLException {
            return false;
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return null;
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return false;
        }
    }
    class ConnectionFactoryMock extends ConnectionFactoryMockAdapter {
        @Override
        public Connection getConnection() {
            return connection;
        }
    }
    class ConnectionMockAdapter implements Connection {

        public Statement createStatement() throws SQLException {
            return null;
        }

        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return null;
        }

        public CallableStatement prepareCall(String sql) throws SQLException {
            return null;
        }

        public String nativeSQL(String sql) throws SQLException {
            return null;
        }

        public void setAutoCommit(boolean autoCommit) throws SQLException {

        }

        public boolean getAutoCommit() throws SQLException {
            return false;
        }

        public void commit() throws SQLException {

        }

        public void rollback() throws SQLException {

        }

        public void close() throws SQLException {

        }

        public boolean isClosed() throws SQLException {
            return false;
        }

        public DatabaseMetaData getMetaData() throws SQLException {
            return null;
        }

        public void setReadOnly(boolean readOnly) throws SQLException {

        }

        public boolean isReadOnly() throws SQLException {
            return false;
        }

        public void setCatalog(String catalog) throws SQLException {

        }

        public String getCatalog() throws SQLException {
            return null;
        }

        public void setTransactionIsolation(int level) throws SQLException {

        }

        public int getTransactionIsolation() throws SQLException {
            return 0;
        }

        public SQLWarning getWarnings() throws SQLException {
            return null;
        }

        public void clearWarnings() throws SQLException {

        }

        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return null;
        }

        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return null;
        }

        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return null;
        }

        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return null;
        }

        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

        }

        public void setHoldability(int holdability) throws SQLException {

        }

        public int getHoldability() throws SQLException {
            return 0;
        }

        public Savepoint setSavepoint() throws SQLException {
            return null;
        }

        public Savepoint setSavepoint(String name) throws SQLException {
            return null;
        }

        public void rollback(Savepoint savepoint) throws SQLException {

        }

        public void releaseSavepoint(Savepoint savepoint) throws SQLException {

        }

        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return null;
        }

        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return null;
        }

        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return null;
        }

        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return null;
        }

        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return null;
        }

        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return null;
        }

        public Clob createClob() throws SQLException {
            return null;
        }

        public Blob createBlob() throws SQLException {
            return null;
        }

        public NClob createNClob() throws SQLException {
            return null;
        }

        public SQLXML createSQLXML() throws SQLException {
            return null;
        }

        public boolean isValid(int timeout) throws SQLException {
            return false;
        }

        public void setClientInfo(String name, String value) throws SQLClientInfoException {

        }

        public void setClientInfo(Properties properties) throws SQLClientInfoException {

        }

        public String getClientInfo(String name) throws SQLException {
            return null;
        }

        public Properties getClientInfo() throws SQLException {
            return null;
        }

        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return null;
        }

        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return null;
        }

        public void setSchema(String schema) throws SQLException {

        }

        public String getSchema() throws SQLException {
            return null;
        }

        public void abort(Executor executor) throws SQLException {

        }

        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

        }

        public int getNetworkTimeout() throws SQLException {
            return 0;
        }

        public <T> T unwrap(Class<T> iface) throws SQLException {
            return null;
        }

        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return false;
        }
    }
    class ConnectionFactoryMockAdapter extends SQLConnectionFactory {
        @Override
        public Connection getConnection() throws SQLException {
            return new ConnectionMock();
        }
    }
}