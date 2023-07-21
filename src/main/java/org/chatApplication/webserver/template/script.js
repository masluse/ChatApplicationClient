document.addEventListener("DOMContentLoaded", function () {
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
});