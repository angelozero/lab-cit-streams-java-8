# lab-cit-streams-java-8

Apresentaçao LAB - CIT Streams Java 8

# Consumer

- A partir do Java 8 temos acesso a um novo método nessa nossa lista: o forEach. *Ex.: `usuarios.forEach(...);`*
- Para cada usuário, o que ele deve fazer? Imprimir o nome. Mas qual é o argu- mento que esse método forEach recebe? Ele recebe um objeto do tipo java.util.function.Consumer, que tem um único método, o accept. Ela é uma nova interface do Java 8
- Criando um Consumer 

```java
public class UserConsumer implements Consumer<User> {

    @Override
    public void accept(User user) {
        System.out.println("Name: ... " + user.getName());
    }
}
```

- Esta classe é bem trivial, possuindo o único método accept responsável por pegar um objeto do tipo Usuario e consumi-lo. *"Consumir"* aqui é realizar alguma tarefa que faça sentido

```java
UserConsumer userConsumer = new UserConsumer();

usersList.forEach(userConsumer);
```

- O código ainda está grande. Parece que o for de maneira antiga era mais su- cinto. Podemos reduzir um pouco mais esse código, evitando a criação da variável local `userConsumer`.

```java
usersList.forEach(new Consumer<User>(){
    @Override
    public void accept(User user){
            System.out.println("Points: ..."+user.getPoints());
        }
    }
);
```

# Lambda

- Lambda no Java é uma maneira mais simples de implementar uma interface que só tem um único método.
- O trecho de código a seguir é um lambda do Java 8. O compilador percebe que você o está atribuindo a um `Consumer<User>` e vai tentar jogar esse código no único método que essa interface define. Repare que não foi citado nem a existência do método accept! Isso é inferido durante o processo de compilação.

```java
Consumer<User> viewUsers = (User u) -> System.out.println(u.getName());

USERS_LIST.forEach(viewUsers);
```

- Então `u -> System.out.println(u.getName())` infere pro mesmo lambda que `(User u) -> {System.out.println(u.getName());}`, se forem atribuídos a um `Consumer<User>`. Podemos passar esse trecho de código diretamente para `USERS_LIST.forEach` em vez de declarar a variável temporária mostrador:

```java
 USERS_LIST.forEach(user -> System.out.println(user.getName()));
```

# Interfaces Funcionais

- Repare que a interface `Consumer<User>`, tem apenas um único método abstrato, o `accept`. É por isso que ao usar o método forEach o compilador sabe exatamente qual método deverá ser implementado com o corpo do lambda:
```java
@FunctionalInterface
public interface Consumer<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t);
```

- Mas e se a interface Consumer<User> tivesse dois métodos?

- O fato de essa interface ter apenas um método é um requisito para que o compilador consiga traduzi-la para uma expressão lambda. Ou seja toda interface do Java que possui apenas um método abstrato pode ser instanciada como um código lambda!

- Isso vale até mesmo para as interfaces antigas, pré-Java 8, como por exemplo o `Runnable`:


```java
public interface Runnable {
    public abstract void run();
}
```

- A interface `Runnable` tem apenas um único método abstrato. Uma interface que se enquadre nesse requisito é agora conhecida como uma interface funcional!

```java
    Runnable r1 = new Runnable(){
        public void run(){
            for (int i = 0; i <= 1000; i++) {
                System.out.println(i);
            } }
    };
    new Thread(r1).start();
    
    // reducing the expression...
    Runnable r2 = () -> {
        for (int i = 0; i <= 1000; i++) {
            System.out.println(i);
        }
    };
    new Thread(r2).start();

    // reducing the expression even more...
    new Thread(() -> {
        for (int i = 0; i <= 1000; i++) {
            System.out.println(i);
        }
    }).start();
```

# Sua própria interface funcional

- Não ha nada de especial para que uma interface seja considerada funcional. O compilador já identifica esse tipo de interface pela sua estrutura.

```java
public interface UserNameAndPoints<T> {
    boolean getInfo(T t);
}
```
```java
UserNameAndPoints<User> userInfo1 = new UserNameAndPoints<User>() {
    @Override
    public String getInfo(User user) {
        return "User name: " + user.getName() + " --- User points: " + user.getPoints();
    }
};
```

- E como podemos usar essa interface com Lambda a partir do Java 8? O que precisamos modificar em sua declaração?
```java
UserNameAndPoints<User> userInfo2 = user ->
        "User name: " + user.getName() + " --- User points: " + user.getPoints();
```

# A anotação @FunctionalInterface

- Podemos marcar uma interface como funcional explicitamente, para que o fato de ela ser uma interface funcional não seja pela simples coincidência de ter um único método. Para fazer isso, usamos a anotação `@FuncionalInterface`:
```java
@FunctionalInterface
public interface UserNameAndPoints<T> {
    String getInfo(T t);
}

```
- Mas qual a diferença de se usar uma interface com apenas um método e com a _annotation @FunctionalInterface_ ?
- Basicamente uma interface com apenas um método será considerada como uma interface funcional, mas se caso adicionarmos um método novo ela deixa de ser uma interface funcional. A _annotation_ basicamente diz que aquela interface irá ter unica e exclusivamente apenas um método.
- _Ex.: Caso tentar usar a annotation com mais de um método_
```java
// An informative annotation type used to indicate that an interface type declaration is 
// intended to be a functional interface as defined by the Java Language Specification
@FunctionalInterface
public interface UserNameAndPoints<T> {
    String getInfo(T t);

    String test();
}
```
- _Gera o erro:_
```shell
java: Unexpected @FunctionalInterface annotation
  UserNameAndPoints is not a functional interface
    multiple non-overriding abstract methods found in interface Example
```

- A _annotation `@FunctionalInterface`_  serve apenas para que ninguém torne aquela interface em não funcional acidentalmente. Ela é opcional justamente para que as interfaces das anti- gas bibliotecas possam também ser tratadas como lambdas, independente da anota- ção, bastando a existência de um único método abstrato.

- Interfaces funcionais são o coração do recurso de Lambda. O Lambda por si só não existe, e sim expressões lambda, quando atribuídas/inferidas a uma interface funcional. Durante o desenvolvimento do Java 8, o grupo que tocou o Lambda chamava essas interfaces de **SAM Types (Single Abstract Method)**.

# O método forEach na interface Iterable ( Default Methods )

- Até agora engolimos o método forEach invocado em nossa List. De onde ele vem, se sabemos que até o Java 7 isso não existia?
  Uma possibilidade seria a equipe do Java tê-lo declarado em uma interface, como na própria List. Qual seria o problema? Todo mundo que criou uma classe que implementa List precisaria implementá-lo. O trabalho seria enorme, mas esse não é o principal ponto. Ao atualizar o seu Java, bibliotecas que têm sua própria List, como o Hibernate, teriam suas implementações quebradas, faltando métodos, podendo gerar os assustadores NoSuchMethodErrors.
  Como adicionar um método em uma interface e garantir que todas as imple- mentações o possuam implementado? Com um novo recurso, declarando código dentro de um método de uma interface!
  Por exemplo, o método forEach que utilizamos está declarado dentro de java.lang.Iterable, que é mãe de Collection, por sua vez mãe de List.
- Mas e de onde vem o método `forEach()`, se sabemos que até o Java 7 isso não existia? Como adicionar um método em uma interface e garantir que todas as implementações o possuam implementado?
  Com um novo recurso, declarando código dentro de um método de uma interface!
  Por exemplo, o método forEach que utilizamos está declarado dentro de java.lang.Iterable, que é mãe de Collection, por sua vez mãe de List.
  Esses métodos são chamados de **default methods** (na documentação do beta pode ser também encontrado **defender methods** ou ainda **extension methods**).
  Como ArrayList implementa List, que é filha (indireta) de _Iterable_, a _ArrayList_ possui esse método, quer ela queira ou não.

```java

/** Iterable **/
public interface Iterable<T> {
     
    // some code here...
    
    default void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (T t : this) {
            action.accept(t);
        }
    }
}

/** Collection **/
public interface Collection<E> extends Iterable<E> {
    // some code here...
}

/** List **/
public interface List<E> extends Collection<E> {
    // some code here...
}

```

 - *O Consumer não tem mais de um método? Então como funciona?*... Uma interface funcional é aquela que possui apenas um método *abstrato*! Ela pode ter sim mais métodos, desde que sejam métodos *default*.
```java
@FunctionalInterface
public interface Consumer<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t);

    /**
     * Returns a composed {@code Consumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code Consumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default Consumer<T> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }
}
```

```java
Consumer<User> showName = user -> System.out.println("Name:   " + user.getName());
Consumer<User> showPoints = user -> System.out.println("Points: " + user.getPoints() + "\n");

USERS_LIST.forEach(showName.andThen(showPoints));
```

