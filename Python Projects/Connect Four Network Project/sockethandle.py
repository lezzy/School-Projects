import consoleinterface
import socket
import collections

c4connection = collections.namedtuple('c4connection', ['socket', 'socket_input', 'socket_output'])


def server_reply(connection: c4connection):
    return connection.socket_input.readline()[:-1]

def user_send(connection: c4connection, line_input: str):
    connection.socket_output.write(line_input + '\r\n')
    connection.socket_output.flush()

    
def startup(connection: c4connection, username):
    user_send(connection, 'I32CFSP_HELLO ' + username)
    print('Server: ' + server_reply(connection))
    user_send(connection, 'AI_GAME')
    print('Server: ' + server_reply(connection))
    
    
    
    
def connect(host: str, port: int):
    server_socket = socket.socket()
    server_socket.connect((host, port))
    server_socket_input = server_socket.makefile('r')
    server_socket_output = server_socket.makefile('w')

    

    return c4connection(socket = server_socket, socket_input = server_socket_input, socket_output = server_socket_output)

            
def close(connection: c4connection):
    print('Closing server connections.')
    connection.socket_input.close()
    connection.socket_output.close()
    connection.socket.close()
    print('Connections closed.')


