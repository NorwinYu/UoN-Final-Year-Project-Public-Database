package AnalizadorSintactico;

import AnalizadorLexico.Categoria;
import AnalizadorLexico.Token;
import java.util.ArrayList;

public class AnalizadorSintactico {

    private ArrayList<Token> tablaSimbolos;
    private ArrayList<ErrorSintactico> tablaErrores;
    private int posicionActual;
    private Token tokenActual;
    private UnidadDeCompilacion unidadDeCompilacion;

    public AnalizadorSintactico(ArrayList<Token> tablaSimbolos) {
        this.tablaSimbolos = tablaSimbolos;
        this.tokenActual = tablaSimbolos.get(posicionActual);
        this.tablaErrores = new ArrayList<>();
    }

    public void analizar() {
        this.unidadDeCompilacion = esUnidadDeCompilacion();
    }

    /**
     * <UnidadCompilación> ::= <ListaFunciones>
     *
     * @return
     */
    public UnidadDeCompilacion esUnidadDeCompilacion() {

        ArrayList<Funcion> ea = esListaFunciones();

        if (ea != null) {
            return new UnidadDeCompilacion(ea);
        }
        else {
            reportarError("Falta al menos una función");
        }

        return null;
    }

    /**
     * <ListaDeclaracion> ::= <DeclaracionCampo>[<ListaDeclaracion>]
     *
     * @return
     */
    public ArrayList<DeclaracionVariable> esListaDeclaraciones() {
        ArrayList<DeclaracionVariable> lista = new ArrayList<>();

        DeclaracionVariable declaracionCampo = esDeclaracionVariable();

        if(declaracionCampo != null)
        {
            while (declaracionCampo != null) {
             lista.add(declaracionCampo);
             declaracionCampo = esDeclaracionVariable();
            }
        }
        
        
        else {
          reportarError("Debe haber al menos una declaracion");
        }
        

        return lista;
    }

    /**
     * <EA> ::= <Termino>[op.Arit<EA>] | "("<EA>")"[op.Arit<EA>]
     *
     * @return
     */
    public ExpresionAritmetica esExpresionAritmetica() {
        int pos = posicionActual;

        Termino termino = esTermino();

        if (termino != null) {
            obtenerSiguienteToken();

            if (tokenActual.getCategoria() == Categoria.OPERADOR_ARITMETICO) {
                Token operadorAritmetico = tokenActual;
                obtenerSiguienteToken();

                ExpresionAritmetica exp = esExpresionAritmetica();

                if (exp != null) {
                    return new ExpresionAritmetica(termino, operadorAritmetico, exp);
                } else {
                    reportarError("Falta una expresión aritmética");
                }

            } else {
                if (tokenActual.getLexema().equals(":")) {
                    System.out.println("bt");
                    hacerBacktracking(pos);
                    return null;
                } else {
                    return new ExpresionAritmetica(termino);
                }

            }

        }

        if (tokenActual.getCategoria() == Categoria.PARENTESIS_ABRIR) {
            obtenerSiguienteToken();

            ExpresionAritmetica ea = esExpresionAritmetica();

            if (ea != null) {

                if (tokenActual.getCategoria() == Categoria.PARENTESIS_CERRAR) {
                    obtenerSiguienteToken();

                    if (tokenActual.getCategoria() == Categoria.OPERADOR_ARITMETICO) {
                        Token operadorAritmetico = tokenActual;
                        obtenerSiguienteToken();

                        ExpresionAritmetica ea2 = esExpresionAritmetica();

                        if (ea2 != null) {
                            return new ExpresionAritmetica(ea, operadorAritmetico, ea2);
                        } else {
                            reportarError("Falta expresión aritmética");
                        }

                    } else {
                        return new ExpresionAritmetica(ea);
                    }

                } else {
                    reportarError("Falta paréntesis derecho");
                }

            } else {
                reportarError("Falta expresión aritmética");
            }

        }

        return null;

    }

    /**
     * <Termino> ::= entero | real | identificador | invocacionMetodo| cadena |
     * booleano | caracter
     *
     * @return
     */
    public Termino esTermino() {
        Invocacion invocacion = esInvocacion();
        if (tokenActual.getCategoria() == Categoria.ENTERO
                || tokenActual.getCategoria() == Categoria.REAL
                || tokenActual.getCategoria() == Categoria.IDENTIFICADOR
                || tokenActual.getCategoria() == Categoria.BOOLEAN
                || tokenActual.getCategoria() == Categoria.CADENA_CARACTERES
                || tokenActual.getCategoria() == Categoria.CARACTER
                || invocacion != null) {
            return new Termino(tokenActual);
        }

        return null;
    }

    /**
     * <DeclaracionVariable> ::= <tipoDato> <ListaVariables> ";"
     *
     * @return
     */
    public DeclaracionVariable esDeclaracionVariable() {

        Token tipoDato = esTipoDato();

        if (tipoDato != null) {
            obtenerSiguienteToken();
            ArrayList<Variable> listaIdent = esListaVariables();

            if (listaIdent != null) {

                if (tokenActual.getCategoria() == Categoria.FIN_SENTENCIA) {
                    obtenerSiguienteToken();
                    return new DeclaracionVariable(tipoDato, listaIdent);
                } else {
                    reportarError("Falta fin de sentencia");
                }
            } else {
                reportarError("Debe escribir al menos un identificador");
            }
        }

        return null;
    }

    /**
     * <ListaVariables> :== <variable> [","<ListaVariables>]
     *
     * @return listaVariables
     */
    public ArrayList<Variable> esListaVariables() {

        ArrayList<Variable> listaVariables = new ArrayList<>();
        Variable variable = esVariable();

        if (variable != null) {

            listaVariables.add(variable);

            if (tokenActual.getCategoria() == Categoria.SEPARADOR) {
                obtenerSiguienteToken();
                listaVariables.addAll(esListaVariables());
            }

        }

        return listaVariables;
    }

    /**
     *
     * < listaParametros>::= <Parametro> | ["," <listaParametros>]
     *
     * @return listaParametros
     */
    public ArrayList<Parametro> esListaParametro() {

        ArrayList<Parametro> listaParametros = new ArrayList<>();
        Parametro parametro = esParametro();

        if (parametro != null) {

            listaParametros.add(parametro);

            if (tokenActual.getCategoria() == Categoria.SEPARADOR) {
                obtenerSiguienteToken();
                listaParametros.addAll(esListaParametro());
            }

        }

        return listaParametros;
    }

    /**
     * <variable> ::= identificador[":"<Termino>]
     *
     * @return Variable(identificador)
     */
    public Variable esVariable() {

        if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
            Token identificador = tokenActual;

            return new Variable(identificador);

        }

        return null;
    }

    /**
     * <Asignacion> ::= identificador operadorAsignacion <Termino> "!"
     *
     * @return ExpresioAsignacion(variable, termino)
     */
    public ExpresioAsignacion esExpresionAsignacion() {

        Variable variable = esVariable();
        if (variable != null) {
            obtenerSiguienteToken();

            if (tokenActual.getCategoria() == Categoria.OPERADOR_ASIGNACION) {
                obtenerSiguienteToken();
                Termino termino = esTermino();

                if (termino != null) {
                    obtenerSiguienteToken();

                    if (tokenActual.getCategoria() == Categoria.FIN_SENTENCIA) {

                        return new ExpresioAsignacion(variable, termino);
                    }
                }
            }
        }
        return null;
    }

    /**
     * <tipoDato> ::= entero | doble | cadena | caracter | buleano
     *
     * @return tokenActual
     */
    public Token esTipoDato() {
        if (tokenActual.getCategoria() == Categoria.TIPO_DATO_ENTERO || tokenActual.getCategoria() == Categoria.TIPO_DATO_DOBLE
                || tokenActual.getCategoria() == Categoria.TIPO_DATO_CARACTER || tokenActual.getCategoria() == Categoria.TIPO_DATO_CADENA) {
            return tokenActual;
        }

        return null;
    }

    /**
     * <tipoRetorno>::= <tipoDato> | vacio
     *
     * @return tokenActual
     */
    public Token esTipoRetorno() {
        if (tokenActual.getLexema().equals(esTipoDato().getLexema()) || tokenActual.getLexema().equals("")) {
            return tokenActual;

        }

        return null;
    }

    /**
     * <Funcion>::= "F" identificador <tipoRetorno> "(" <listaParametros> ")"
     * <listaSentencias> "n" finalizador
     *
     *
     * @return Funcion
     */
    public Funcion esFuncion() {
        if (tokenActual.getLexema().equals("F")) {
            obtenerSiguienteToken();
            if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
                Token identificador = tokenActual;
                obtenerSiguienteToken();

                Token tipoRetorno = esTipoRetorno();
                if (tipoRetorno != null) {
                    obtenerSiguienteToken();

                    if (tokenActual.getCategoria() == Categoria.PARENTESIS_ABRIR) {
                        System.out.println("abrir");
                        obtenerSiguienteToken();
                        ArrayList<Parametro> listaParametro = esListaParametro();

                        if (listaParametro.size() > 0) {
                            System.out.println("para");
                            obtenerSiguienteToken();
                            if (tokenActual.getCategoria() == Categoria.PARENTESIS_CERRAR) {
                                obtenerSiguienteToken();
                                ArrayList<Sentencia> listaSentencias = esListaSentencia();
                                if (listaSentencias.size() > 0) {
                                    obtenerSiguienteToken();
                                    if (tokenActual.getLexema().equals("n")) {
                                        obtenerSiguienteToken();
                                        if (tokenActual.getCategoria() == Categoria.FIN_SENTENCIA) {
                                            System.out.println("EsFuncion");
                                            obtenerSiguienteToken();
                                            return new Funcion(identificador, tipoRetorno, listaParametro, listaSentencias);
                                        } else {
                                            reportarError("Falta fin de sentencia");
                                        }
                                    }
                                }

                            }

                        } else if (tokenActual.getCategoria() == Categoria.PARENTESIS_CERRAR) {
                            System.out.println("cerrar");
                            obtenerSiguienteToken();
                            ArrayList<Sentencia> listaSentencias = esListaSentencia();

                            if (listaSentencias.size() > 0) {
                                System.out.println("Estoy aqui");
                                obtenerSiguienteToken();
                                if (tokenActual.getLexema().equals("n")) {
                                    System.out.println("Vea " + tokenActual.getLexema());
                                    obtenerSiguienteToken();
                                    if (tokenActual.getCategoria() == Categoria.FIN_SENTENCIA) {
                                        System.out.println("EsFuncion");
                                        obtenerSiguienteToken();
                                        return new Funcion(identificador, tipoRetorno, listaSentencias);
                                    } else {
                                        reportarError("Falta fin de sentencia");
                                    }
                                }
                            }

                        }

                    }

                }
            }

        }
        return null;
    }

    /**
     * Metodo que se encarga de saltar a la siguiente posición
     */
    public void obtenerSiguienteToken() {

        if (posicionActual < tablaSimbolos.size() - 1) {
            posicionActual++;
            tokenActual = tablaSimbolos.get(posicionActual);
        } else {
            tokenActual = new Token("", Categoria.FIN_CODIGO, tokenActual.getFila(), tokenActual.getColumna());
        }

    }

    /**
     *
     * Metodo que se encarga de reportar si encuentra algun error al momento de
     * analizar el lexema que hemos ingresado
     *
     * @param mensaje
     */
    public void reportarError(String mensaje) {
        tablaErrores.add(new ErrorSintactico(mensaje, tokenActual.getFila(), tokenActual.getColumna()));
    }

    /**
     * Metodo que se encarga de realizar backtracking en el codigo para buscar
     * otra ruta por la cual pueda analizar el lexema proporcionado
     *
     * @param posInicial
     */
    public void hacerBacktracking(int posInicial) {
        posicionActual = posInicial;
        tokenActual = tablaSimbolos.get(posicionActual);
    }

    public ArrayList<ErrorSintactico> getTablaErrores() {
        return tablaErrores;
    }

    public UnidadDeCompilacion getUnidadDeCompilacion() {
        return unidadDeCompilacion;
    }

    /**
     * <Invocacion>::= "IvF" punto identificador "(" [<listaParametros>] ")"
     * finSentencia
     *
     * @return Invocacion
     */
    public Invocacion esInvocacion() {
        if (tokenActual.getLexema().equals("IvF")) {
            obtenerSiguienteToken();
            if (tokenActual.getCategoria() == Categoria.PUNTO) {
                obtenerSiguienteToken();
                if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
                    Token identificador = tokenActual;
                    obtenerSiguienteToken();

                    if (tokenActual.getCategoria() == Categoria.PARENTESIS_ABRIR) {
                        obtenerSiguienteToken();
                        ArrayList<Argumento> listaArgumentos = esListaArgumento();
                        if (listaArgumentos != null) {
                            obtenerSiguienteToken();
                            if (tokenActual.getCategoria() == Categoria.PARENTESIS_CERRAR) {
                                obtenerSiguienteToken();
                                if (tokenActual.getCategoria() == Categoria.FIN_SENTENCIA) {
                                    return new Invocacion(listaArgumentos);
                                }
                                else{
                                    reportarError("Falta fin de sentecia");
                                }
                            }
                            else{
                                reportarError("Falta parentesis izquierdo");
                            }
                        } else {
                            if (tokenActual.getCategoria() == Categoria.PARENTESIS_CERRAR) {
                                obtenerSiguienteToken();
                                if (tokenActual.getCategoria() == Categoria.FIN_SENTENCIA) {
                                    return new Invocacion();
                                }
                                else{
                                    reportarError("Falta fin de sentecia");
                                }
                            }
                            else{
                                reportarError("Falta parentesis izquierdo");
                            }
                            
                        }
                    }else{
                        reportarError("Falta parentesis derecho");
                    }
                }else {
                    reportarError("Falta identificador función");
                }
            }else {
                reportarError("Falta Punto");
            }
            

        }
        return null;
    }

    /**
     *
     * <Parametro>::= <tipoDato> identificador
     *
     * @return Parametro
     */
    public Parametro esParametro() {
        Token tipo = esTipoDato();
        if (tipo != null) {
            obtenerSiguienteToken();
            if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
                return new Parametro(tipo, tokenActual);
            }
            else{
                reportarError("Falata identificador del parametro");
            }
        }
        
        return null;
    }

    /**
     * <ListaSentencias> ::= <Sentencia> | [<ListaSentencias>]
     *
     * @return listaSentencias
     */
    public ArrayList<Sentencia> esListaSentencia() {
        ArrayList<Sentencia> listaSentencias = new ArrayList<>();
        Sentencia sentencia = esSentencia();

        if (sentencia != null) {

            listaSentencias.add(sentencia);

            if (tokenActual.getCategoria() == Categoria.SEPARADOR) {
                obtenerSiguienteToken();
                listaSentencias.addAll(esListaSentencia());
            }

        }
        else{
            reportarError("Debe haber al menos una sentencia");
        }

        return listaSentencias;
    }

    /**
     *
     * <Sentencia> ::= <SentenciaDesicion> | <DeclaracionVariable> |
     * <ExpresionAsignacion> | <ImprimirDato> |<CicloMientras> |<Retorno> |
     * <LeerDato> |<Expresion>
     *
     * @return
     */
    public Sentencia esSentencia() {

        Sentencia sentencia = null;
        sentencia = esSentanciaDecision();
        if (sentencia != null) {
            System.out.println("1");
            return sentencia;
        }
        sentencia = esDeclaracionVariable();
        if (sentencia != null) {
            System.out.println("2");
            return sentencia;
        }
        sentencia = esExpresion();
        if (sentencia != null) {
            System.out.println("3");
            return sentencia;
        }

        sentencia = esImprimirDato();
        if (sentencia != null) {
            System.out.println("5");
            return sentencia;
        }
        sentencia = esCicloMientras();
        if (sentencia != null) {
            System.out.println("6");
            return sentencia;
        }
        sentencia = esRetorno();
        if (sentencia != null) {
            System.out.println("7");
            return sentencia;
        }
        sentencia = esLeerDato();
        if (sentencia != null) {
            System.out.println("8");
            return sentencia;
        }
        return null;
    }

    /**
     * <ExpresionRelacional> ::= <Termino> OperadorRelacional <Termino>
     *
     * @return ExpresionRelacional
     */
    public ExpresionRelacional esExpresionRelacional() {
        int pos = posicionActual;
        Termino termino = esTermino();
        if (termino != null) {
            obtenerSiguienteToken();

            if (tokenActual.getCategoria() == Categoria.OPERADOR_RELACIONAL) {
                Token operadorRelacional = tokenActual;
                obtenerSiguienteToken();

                Termino termino1 = esTermino();
                if (termino1 != null) {
                    return new ExpresionRelacional(termino, operadorRelacional, termino1);
                } else {
                    reportarError("Falta un termino para completar la expresión relacional");
                }
            } else {
                if (tokenActual.getCategoria() == Categoria.OPERADOR_ASIGNACION) {
                    hacerBacktracking(pos);
                    return null;
                } else {
                    reportarError("Falta un operador relacional en la expresión");

                }

            }

        } else {
            reportarError("Falta un termino para completar la expresión relacional");

        }
        return null;
    }

    /**
     * <SentanciaDecision> ::= "Si" <ExpresionRelacional> "hacer"
     * <listaSentencias> "FinSi" ["Sino" <listaSentencias> "FinSino"]
     *
     * @return SentanciaDecision
     */
    public SentanciaDecision esSentanciaDecision() {

        if (tokenActual.getLexema().equals("Si")) {
            obtenerSiguienteToken();
            ExpresionRelacional expresionRelacional = esExpresionRelacional();

            if (expresionRelacional != null) {
                obtenerSiguienteToken();

                if (tokenActual.getLexema().equals("hacer")) {
                    obtenerSiguienteToken();

                    ArrayList<Sentencia> listaSentencias = esListaSentencia();
                    if (listaSentencias != null) {
                        obtenerSiguienteToken();

                        if (tokenActual.getLexema().equals("FinSi")) {
                            obtenerSiguienteToken();

                            if (!tokenActual.getLexema().equals("Sino")) {
                                return new SentanciaDecision(expresionRelacional, listaSentencias);

                            } else {
                                ArrayList<Sentencia> listaSentencia1 = esListaSentencia();
                                if (listaSentencia1 != null) {
                                    obtenerSiguienteToken();
                                    if (tokenActual.getLexema().equals("FinSino")) {
                                        return new SentanciaDecision(expresionRelacional, listaSentencias, listaSentencia1);
                                    }
                                    else{
                                        reportarError("Falta finalizacion del Sino");
                                    }
                                }
                            }

                        }
                        else{
                            reportarError("Falta finalizacion del Si");
                        }

                    }

                }
                reportarError("Falta palabra reservada \"hacer\"");

            }else {
                reportarError("Falta exprecion relacional");
            }

        }

        return null;

    }

    /**
     * <ImprimirDato> ::= "Imprimir" "(" <Termino> ")" finSentencia
     *
     * @return ImprimirDato
     */
    public ImprimirDato esImprimirDato() {

        if (tokenActual.getLexema().equals("Imprimir")) {
            obtenerSiguienteToken();

            if (tokenActual.getCategoria() == Categoria.PARENTESIS_ABRIR) {
                obtenerSiguienteToken();

                Termino termino = esTermino();
                if (termino != null) {
                    obtenerSiguienteToken();
                    if (tokenActual.getCategoria() == Categoria.PARENTESIS_CERRAR) {
                        obtenerSiguienteToken();

                        if (tokenActual.getCategoria() == Categoria.FIN_SENTENCIA) {
                            return new ImprimirDato(termino);
                        } else{
                            reportarError("Falta fin de sentencia");
                        }
                    }
                    else{
                        reportarError("Falta parentesis izquierdo");
                    }

                } else{
                    reportarError("Falta termino para imprimir");
                }
            }
            else{
                reportarError("Falta parentesis derecho");
            }
        }

        return null;
    }

    /**
     * <CicloMientras> ::= "Mientras" <ExpresionRelacional> "hacer"
     * <ListaSentencias> "FinMientras"
     *
     * @return CicloMientras
     */
    public CicloMientras esCicloMientras() {

        if (tokenActual.getLexema().equals("Mientras")) {
            obtenerSiguienteToken();

            ExpresionRelacional expresionRelacional = esExpresionRelacional();
            if (expresionRelacional != null) {
                obtenerSiguienteToken();

                if (tokenActual.getLexema().equals("hacer")) {
                    obtenerSiguienteToken();

                    ArrayList<Sentencia> listaSentencias = esListaSentencia();
                    if (listaSentencias != null) {
                        obtenerSiguienteToken();

                        if (tokenActual.getLexema().equals("FinMientras")) {
                            return new CicloMientras(expresionRelacional, listaSentencias);
                        }else{
                            reportarError("Falta finMientras");
                        }

                    }
                }else{
                    reportarError("Falata palabra reservada \"hacer\"");
                }

            } else{
                reportarError("Falta exprecion relacional");
            }

        }

        return null;

    }

    /**
     * <Retorno> ::= "retorno" <Termino> finSentencia
     *
     * @return esRetorno
     */
    public Retorno esRetorno() {

        if (tokenActual.getLexema().equals("retorno")) {
            obtenerSiguienteToken();

            Termino termino = esTermino();
            if (termino != null) {
                obtenerSiguienteToken();

                if (tokenActual.getCategoria() == Categoria.FIN_SENTENCIA) {
                    return new Retorno(termino);
                }
                else{
                    reportarError("Falta fin de sentencia");
                }
            }else{
                reportarError("Falta termino para retornar");
            }

        }

        return null;
    }

    /**
     * <LeerDato> ::= <Variable> punto "leer"
     *
     * @return LeerDato
     */
    public LeerDato esLeerDato() {

        if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
            Token identificador = tokenActual;
            obtenerSiguienteToken();

            if (tokenActual.getCategoria() == Categoria.PUNTO) {
                obtenerSiguienteToken();

                if (tokenActual.getLexema().equals("leer")) {
                    return new LeerDato(identificador);

                }else{
                    reportarError("Falta palabra reservada leer");
                }
            }else{
                reportarError("Falta punto");
            }

        }

        return null;
    }

    /**
     * <ListaFunciones> ::= <Funcion> | [<ListaFunciones>]
     *
     * @return listaFunciones
     */
    public ArrayList<Funcion> esListaFunciones() {
        ArrayList<Funcion> listaFunciones = new ArrayList<>();

        Funcion funcion = esFuncion();
        if(funcion != null){
             while (funcion != null) {
            listaFunciones.add(funcion);
            funcion = esFuncion();
        }
        }
        else{
            reportarError("Febe haber al menos una funcion");
        }

        

        return listaFunciones;
    }

    /**
     * <Argumento> ::= identificador Op. Asignacion <Termino>
     *
     * @return Argumento
     */
    public Argumento esArgumento() {
        if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
            Token identificador = tokenActual;
            obtenerSiguienteToken();
            if (tokenActual.getCategoria() == Categoria.OPERADOR_ASIGNACION) {
                obtenerSiguienteToken();
                Termino termino = esTermino();
                if (termino != null) {
                    return new Argumento(identificador, termino);

                }else{
                    reportarError("Falta termino");
                }
            }else{
                reportarError("Falta operador Asignacion");
            }
        }
        return null;
    }

    /**
     * <ListaArgumentos> ::= <Argumento> | ["," <ListaArgumentos>]
     *
     * @return ListaArgumento
     */
    public ArrayList<Argumento> esListaArgumento() {
        ArrayList<Argumento> listaArgumentos = new ArrayList<>();
        Argumento argumento = esArgumento();

        if (argumento != null) {

            listaArgumentos.add(argumento);

            if (tokenActual.getCategoria() == Categoria.SEPARADOR) {
                obtenerSiguienteToken();
                listaArgumentos.addAll(esListaArgumento());
            }

        }

        return listaArgumentos;
    }

    /**
     * <Expresion> ::= <ExpresionAritmetica> | <ExpresionAsignacion> |
     * <ExpresionCadena> | <ExpresionRelacional>
     *
     * @return expresion
     */
    public Expresion esExpresion() {

        Expresion expresion = null;

        expresion = esExpresionAsignacion();
        if (expresion != null) {
            return expresion;
        }
        expresion = esExpresionAritmetica();
        if (expresion != null) {
            return expresion;
        }
        expresion = esExpresionCadena();
        if (expresion != null) {
            return expresion;
        }
        expresion = esExpresionRelacional();
        if (expresion != null) {
            return expresion;
        }
        return null;
    }

    /**
     * <ExpresionCadena> ::= cadena ["+" <Termino>]
     *
     * @return ExpresionCadena
     */
    public ExpresionCadena esExpresionCadena() {
        if (tokenActual.getCategoria() == Categoria.CADENA_CARACTERES) {
            Token cadena = tokenActual;
            obtenerSiguienteToken();
            if (tokenActual.getLexema().equals("+")) {
                obtenerSiguienteToken();
                Termino termino = esTermino();
                if (termino != null) {
                    return new ExpresionCadena(cadena, termino);
                }

            } else {
                return new ExpresionCadena(cadena);
            }
        }

        return null;
    }

}
