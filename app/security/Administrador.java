package security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Etiqueta para marcar actions que só o ADMIN pode executar. "@Administrador" 

@Target({ElementType.METHOD, ElementType.TYPE}) // pode ser usada em método ou classe
@Retention(RetentionPolicy.RUNTIME)// fica disponível em tempo de execução
public @interface Administrador {

}
