# Data Analyzer

This project intends to create a simple .dat files analyzer.

## Summary

  - [Introduction](#introduction)
  - [Requirements](#requirements)
  - [Installation](#installation)
  - [Running](#running)
  - [Author](#author)
  
  ## Introduction
  
  This application is able to process all .dat files at %HOMEPATH%/data/in folder.
  Those files contains data about salesman, customer and sale.
  
  At %HOMEPATH%/data/in we can store many .dat files with the following layout, for example: 
  ```
  001ç1234567891234çDiegoç50000 
  001ç3245678865434çRenatoç40000.99
  002ç2345675434544345çJose da SilvaçRural
  002ç2345675433444345çEduardoPereiraçRural
  003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego
  003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato
```

Representing a salesman:
```
001ç1234567891234çDiegoç50000

Salesman identificator on file : 001
CPF: 1234567891234
Name: Diego
Salary: 50000
```

Representing a customer:
```
002ç2345675434544345çJose da SilvaçRural

Customer identificator on file : 002
CNPJ: 2345675434544345
Name: Jose da Silva
```

Representing a sale:
```
003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego

Sale identificator on file : 003
Sale ID: 10
Item list: [1-10-100,2-30-2.50,3-40-3.10]
Salesman Name: Diego
```

All files could have any separator such as : ç ; , / and etc...

When we run the application it should run without end. As we add filename.dat files at %HOMEPATH%/data/in directory
we can see the filename.done.dat generated file at %HOMEPATH%/data/out with those requirements:
```
Customer quantity,
Salesman quantity,
Most expansive sale ID,
Worse salesman name
```

PS: If you add more than one file there's no problem, the application should rerun and process all the data and update the filename.done.dat file.

  
  ## Requirements
  
  - [Java JDK](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
  - [Git](https://git-scm.com/)
  - [Gradle](https://gradle.org/)
  
  
  ## Installation
  
  - First you should have the JDK installed on your computer, to do so access: [Installing JDK](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/)
  
  - And then, install Git:
  ```
  $apt-get install git
  ```

  
  - Clone the project from github:
  
  ```
$git clone https://github.com/fabioqmarsiaj/ilegra-challenge
``` 
  
  - Then, install gradle following the corresponding commands:
  ```
   $sudo apt install gradle
``` 

- Check gradle version:
 ```
  $gradle -v
```
  
   
   ## Running
    
   - On command line do as follow:
   
   ```
    $cd ilegra-challange
    $gradle build
    $gradle run
   ```

   - PS: By default, the application creates the directories for you and an empty flat file inside %HOMEPATH%/data/in called data.dat.
   If you don't add any files before you run the application, it will crash with an exception called "DataFileEmptyException".
   Just go there and add the datas, or a whole new file.
   
   ## Author
   
   * **Fabio Quinto Marsiaj** -  [GitHub](https://github.com/fabioqmarsiaj)
   
   <a href="https://github.com/fabioqmarsiaj">
     <img 
     alt="Imagem do Autor Fabio Marsiaj" src="https://avatars0.githubusercontent.com/u/34289167?s=460&v=4" width="100">
   </a>
  
