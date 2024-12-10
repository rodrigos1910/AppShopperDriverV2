# App Shopper Driver

## Descrição
O projeto **App Shopper Driver** é um aplicativo voltado para motoristas que realizam serviços de entrega e corridas. Ele fornece funcionalidades como estimativa de corridas, confirmação de solicitações, histórico de corridas e gerenciamento de motoristas. Este projeto está desenvolvido em Kotlin e utiliza o padrão MVVM, integrando-se com uma API RESTful para a comunicação.

## Funcionalidades
- Estimar uma corrida com base na origem e destino.
- Confirmar solicitações de corrida.
- Exibir histórico de corridas realizadas.
- Gerenciar motoristas no banco de dados local.

## Tecnologias Utilizadas
- **Linguagem**: Kotlin
- **Arquitetura**: MVVM (Model-View-ViewModel)
- **API Retrofit**: Integração com APIs RESTful
- **Room Database**: Banco de dados local
- **Material Design Components**: Interface moderna e intuitiva
- **Gson**: Serialização e desserialização de JSON

## Dependências
Certifique-se de adicionar as seguintes dependências no arquivo `build.gradle` do módulo:

### Lista de Dependências e Funções

1. **`libs.androidx.core.ktx`**
    - Facilita o uso de funcionalidades do Android com extensões Kotlin.

2. **`libs.androidx.appcompat`**
    - Fornece suporte a componentes de interface do Android em versões antigas.

3. **`libs.material`**
    - Implementa componentes de design seguindo as diretrizes do Material Design.

4. **`libs.androidx.activity`**
    - Facilita a integração de `ViewModels` e `Lifecycle` com `Activity`.

5. **`libs.androidx.constraintlayout`**
    - Permite criar layouts responsivos e complexos usando `ConstraintLayout`.

6. **`libs.androidx.navigation.fragment.ktx`**
    - Extensões Kotlin para a navegação entre fragmentos.

7. **`libs.androidx.navigation.ui.ktx`**
    - Extensões Kotlin para controle de navegação na interface do usuário.

8. **`libs.osmdroid.android`**
    - Biblioteca de mapas gratuita baseada no OpenStreetMap, alternativa ao Google Maps.

9. **`libs.play.services.location`**
    - Gerencia localização e serviços relacionados ao Google Play.

10. **`libs.androidx.cardview`**
    - Fornece suporte para o uso de `CardView` no design.

11. **`libs.androidx.room.ktx`**
    - Extensões Kotlin para o uso do Room como banco de dados local.

12. **`libs.androidx.room.runtime`**
    - Runtime necessário para o funcionamento do Room Database.

13. **`libs.core.testing`**
    - Facilita a execução de testes para componentes principais do Android.

14. **`libs.core.ktx`**
    - Extensões adicionais do núcleo do Android para Kotlin.

15. **`libs.androidx.room.compiler`**
    - Necessário para gerar código para o Room Database.

16. **`libs.retrofit`**
    - Biblioteca para comunicação com APIs RESTful.

17. **`libs.converter.gson`**
    - Conversor para serializar e desserializar objetos JSON no Retrofit.

18. **`libs.adapter.rxjava2`**
    - Integração do Retrofit com RxJava para manipulação de fluxos reativos.

19. **`libs.junit`**
    - Framework para testes unitários em Java.

20. **`libs.mockito.core`**
    - Biblioteca para criação de mocks em testes unitários.

21. **`libs.coroutines.test`**
    - Suporte para testes de corrotinas no Kotlin.

22. **`libs.mockito.inline`**
    - Extensão do Mockito para mocks mais dinâmicos.

23. **`libs.robolectric`**
    - Framework para executar testes unitários simulando o ambiente Android.

24. **`libs.androidx.junit`**
    - Extensão do JUnit para testes instrumentados no Android.

25. **`libs.espresso.core`**
    - Framework para testar interações de interface no Android.

26. **`libs.espresso.contrib`**
    - Extensões adicionais para o Espresso, como suporte a RecyclerView.


## Versão do Android
- Mínima: API 24 (Android 7.0 Nougat)
- Alvo: API 34 (Android 14)

## Versão do Java
- Requer **Java 17**. Para simular o testes unitários usando Robolectric. Configure isso no Android Studio em:
    - `File` > `Settings` > `Build, Execution, Deployment` > `Build Tools` > `Gradle`.

## Como Rodar o Projeto
1. Clone o repositório:
   ```bash
   git clone https://github.com/rodrigos1910/AppShopperDriverV2.git
   ```
2. Abra o projeto no Android Studio.
3. Certifique-se de que todas as dependências estejam instaladas.
4. Conecte um dispositivo ou inicie um emulador Android.
5. Clique em "Run" ou use o atalho **Shift + F10**.

## Configuração da API
A aplicação consome uma API RESTful. Certifique-se de configurar corretamente o endpoint no arquivo `RetrofitClient`:

```kotlin
private const val BASE_URL = "https://sua-api-endpoint.com/"
```

## Licença
Este projeto está licenciado sob a Licença MIT. Consulte o arquivo [LICENSE](LICENSE) para mais informações.

