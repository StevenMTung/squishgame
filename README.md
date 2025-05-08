![Squish Game Image](https://github.com/user-attachments/assets/7d98d66f-b258-4da2-9d12-a945b3e89631)

<h1>Descrição do projeto</h1>

O **Squish Game** é um jogo inspirado na série Squid Game (Round 6)! <br> 
O jogo consiste na interação do usuário com a câmera frontal do celular, em que é feito a detecção de movimento do corpo com a **API de Detecção de Poses do Google ML Kit**.<br>
<p>Para jogar o usuário precisa mexer os braços, em frente à câmera, como se estivesse correndo para que o seu personagem se movimente de uma extremidade da tela até a outra.</p>
Enquanto o jogador tenta alcançar o objetivo final, há uma boneca que alterna turnos cantando e observando o jogador.<br> 
Como regra do jogo só é permitida a movimentação do usuário enquanto a boneca está cantando. 
Caso haja movimentação quando a boneca para de cantar e se vira para observar o jogador, o personagem é eliminado e a partida termina.<br> 
O objetivo do jogo é movimentar o personagem de uma extremidade da tela até a outra sem ser eliminado pela boneca.

<h1>Funcionalidades</h1>

- `Instruções do jogo`:

![Image](https://github.com/user-attachments/assets/87d409d6-4650-43ad-b08e-c0136d970b3a)
- `Quadro dos melhores jogadores`:

![Image](https://github.com/user-attachments/assets/654df07a-8a95-4530-a48c-35c7808d1428)

<h1>Jogabilidade</h1>

- `Para movimentar o personagem é necessário mexer os braços como se estivesse correndo`:

 ![Image](https://github.com/user-attachments/assets/7af7cc43-a137-469d-b31f-d8c978c06fb4)
- `É proibido se movimentar quando a boneca para de cantar e se vira para observar o personagem. Caso haja movimentação o jogador é eliminado`:

![Image](https://github.com/user-attachments/assets/fcb36b38-86f3-4ccb-9b5b-2c5abe417d64)
- `Para vencer a partida basta movimentar o personagem de uma extremidade da tela até a outra sem ser eliminado`:

![Image](https://github.com/user-attachments/assets/94af3e5a-811a-4680-a58d-85d528f4acb6)  
- `Se o jogador for rápido o bastante ele entra no quadro do Top 5 melhores jogadores:`

![Image](https://github.com/user-attachments/assets/f39344a3-bee6-49a1-bf73-0b151cee8a42)
 
  <h1>Técnicas e tecnologias utilizadas</h1>

O App foi desenvolvido com as seguintes tecnologias:

- `Dagger-Hilt`: Injeção de dependência
- `Jetpack Compose`: Implementação da interface de usuário
- `ViewModel e uiState`: Gerenciamento de Estados de tela
- `Navigation com NavHost`: Navegações entre telas por grafos hospedados em um NavHost
- `Coroutines e Flow`: Rodar as operações de forma assíncrona e reativas
- `Room`: Armazenamento de dados persistentes dentro do jogo
- `ML Kit API de Detecção de Poses`: Detectar e identificar pontos do corpo humano
- `CameraX`: Acesso à câmera do dispositivo
- `MVVM`: Padrão de arquitetura Model-View-ViewModel

<h1>Acesso ao projeto</h1>

Você pode acessar o [código fonte do projeto](https://github.com/StevenMTung/squishgame) ou [baixá-lo](https://github.com/StevenMTung/squishgame/archive/refs/heads/main.zip).

<h1>Abrir e rodar o projeto</h1> 

Após baixar o projeto, você pode abrir com o `Android Studio`. Para isso, na tela de launcher clique em:

- `Open an Existing Project` (ou alguma opção similar);
- Procure o local onde o projeto está e o selecione (Caso o projeto seja baixado via zip, é necessário extraí-lo antes de procurá-lo);
- Por fim clique em `OK`.

O `Android Studio` deve executar algumas tasks do *Gradle* para configurar o projeto, aguarde até finalizar. Ao finalizar as tasks, você pode executar o App 🏆 

<h1>Autor</h1>

 [<img loading="lazy" src="https://avatars.githubusercontent.com/u/134224337?v=4" width=115><br><sub>Steven Marc Tung</sub>](https://github.com/StevenMTung)
| :---: | 
