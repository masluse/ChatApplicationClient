document.addEventListener("DOMContentLoaded", function () {
    loadChatHistory();

    const sendButton = document.getElementById("send-btn");
    const chatBox = document.getElementById("chat-box");

    sendButton.addEventListener("click", function () {
        const inputElement = document.querySelector(".input-group input");
        const message = inputElement.value.trim();

        if (message !== "") {
            const messageDiv = document.createElement("div");
            messageDiv.classList.add("alert", "alert-primary", "my-2");
            messageDiv.innerText = message;

            chatBox.appendChild(messageDiv);
            inputElement.value = "";
        }
    });

    document.querySelector(".input-group input").addEventListener("keypress", function (event) {
        if (event.key === "Enter") {
            sendButton.click();
        }
    });

    function loadChatHistory() {
        fetch('http://localhost:8000') // Replace this with the URL of your server
            .then(response => response.json())
            .then(data => {
                data.forEach(message => {
                    const messageDiv = document.createElement("div");
                    messageDiv.classList.add("alert", "alert-primary", "my-2");
                    messageDiv.innerText = `${message.user} (${new Date(message.timestamp).toLocaleTimeString()}): ${message.message}`;

                    chatBox.appendChild(messageDiv);
                });
                chatBox.scrollTop = chatBox.scrollHeight;
            });
    }
});