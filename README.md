# GRpc sample project

This project covers sample wallet application that is implemented by structure

Modules that are used in projects are;
 - spring-boot
 - GRpc
 - spring-data-jpa
 - JUnit

##### Focused on

  - Protocol Buffers
  - Multi-threads
  - Transaction handling
  - N-tier architecture
  - Interceptors

#### Run the project
 - Make sure that MySQL server installed on your pc
 (https://dev.mysql.com/downloads/mysql/)
 
 - Go to project folder and run
```sh
gradle build
```
 - Get the Grpc client. I use Evans
 (https://github.com/ktr0731/evans)
 
 - Go to src/main/proto/wallet/ folder and run below command on CLI 

```sh
evans wallet.proto -p 6565
```
 - Following view should be appeared if everything installed properly
 <img src="https://github.com/orkun34/GRpc/blob/master/images/evans_cli.png?sanitize=true&raw=true" />

 - After that you are able to list services separately by following commands

```sh
show service
```  
 - If you want to run random operation with parameters (#users,#threads,#rounds),
 ```sh
 call ClientCLI
 ``` 
 It should be shown such as;
  <img src="https://github.com/orkun34/GRpc/blob/master/images/evans_cli_2.png?sanitize=true&raw=true" />
