# Chat-Application
The system consists of two chat servers connected together, Each chat server can handle multiple clients and is connected to the other server.

Client Functionalities:

1. Join(name​): The client uses this message to log on to the server (i.e., initiate a chat session).The client
must send this message first, before sending any other messages. The member name is any name the
client chooses to be identified by.

2. GetMemberList():​ This message asks for a list of all members on the network.

3. Chat (Source, Destination, TTL, Message):​ The chat message that the user will send to another user.

- Source: The id of the sender.

- Destination: The id of the receiver.

- Time To Live (TTL): TTL is a counter which is decremented at each chat server, when it reaches zero the
message should be discarded and an error message should be sent to the sender. The purpose of TTL is
to prevent the message from looping infinitely in the network. The default value for the TTL is set to 2.

4. Quit:​ This message is used to log off


