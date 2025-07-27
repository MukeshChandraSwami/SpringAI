import React, { useState } from 'react';
import { v4 as uuidv4 } from 'uuid';
import './App.css';

function App() {
    const [chats, setChats] = useState([]);
    const [currentChatId, setCurrentChatId] = useState(null);
    const [message, setMessage] = useState('');

    const handleNewChat = () => {
        const title = prompt('Enter a title for the new chat:');
        if (!title) return;
        const newId = uuidv4();
        setChats([{ id: newId, title, messages: [] }, ...chats]);
        setCurrentChatId(newId);
    };

    const handleSend = () => {
        setChats(chats.map(chat =>
            chat.id === currentChatId
                ? { ...chat, messages: [...chat.messages, { sender: 'user', text: message }] }
                : chat
        ));
        setMessage('');
    };

    const currentChat = chats.find(chat => chat.id === currentChatId);

    return (
        <div className="container">
            <div className="sidebar">
                <button className="new-chat" onClick={handleNewChat}>+ New Chat</button>
                <div className="chat-list">
                    {chats.map(chat => (
                        <div
                            key={chat.id}
                            className={`chat-item ${chat.id === currentChatId ? 'active' : ''}`}
                            onClick={() => setCurrentChatId(chat.id)}>
                            {chat.title || `Chat ${chat.id.slice(0, 8)}`}
                        </div>
                    ))}
                </div>
            </div>
            <div className="chat-area">
                <div className="messages">
                    {currentChat?.messages.map((msg, idx) => (
                        <div key={idx} className={`message ${msg.sender}`}>{msg.text}</div>
                    ))}
                </div>
                {currentChat && (
                    <div className="input-area">
                        <input
                            type="text"
                            value={message}
                            onChange={(e) => setMessage(e.target.value)}
                            placeholder="Type your message..."
                        />
                        <button onClick={handleSend}>Send</button>
                    </div>
                )}
            </div>
        </div>
    );
}

export default App;