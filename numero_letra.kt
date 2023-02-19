package com.example.unidad_2

fun main()
{
    var numero = ""
    while (!esnumero(numero))
    {
        println("Ingresaun munero:")
        numero = readln()

        if(!esnumero(numero))
            println("No es un numero.")
    }

    val delim = "."
    val separadecimales = numero.split(delim)

    if(separadecimales.size == 1)
        println(convercion(numero))
    else
        println(convercion(separadecimales[0]) + "punto " + convercion(separadecimales[1]))
}

fun esnumero(numero : String) : Boolean
{
    return try
    {
        numero.toFloat()
        true
    }
    catch (e : Exception)
    {
        false
    }
}

fun convercion(numero: String): String
{
    return if (numero.toInt() == 0)//si el valor es cero
        "cero "
    else if (numero.toInt() > 999999) //si es millon
        getMillones(numero)
    else if (numero.toInt() > 999) //si es miles
        getMiles(numero)
    else if (numero.toInt() > 99) //si es centena
        getCentenas(numero)
    else if (numero.toInt() > 9) //si es decena
        getDecenas(numero)
    else //sino unidades -> 9
        getUnidades(numero)
}

fun getUnidades(numero : String) : String
{
    val unidades = arrayOf("cero", "uno ", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ", "nueve ")

    val numbers = (numero.substring(numero.length - 1)).toInt()

    return unidades[numbers]
}

fun getDecenas(numero: String) : String
{
    val decenas = arrayOf("diez ", "once ", "doce ", "trece ", "catorce ", "quince ", "dieciseis ",
        "diecisiete ", "dieciocho ", "diecinueve", "veinte ", "treinta ", "cuarenta ",
        "cincuenta ", "sesenta ", "setenta ", "ochenta ", "noventa ")

    return if (numero.toInt() < 10) //casos como -> 01 - 09
        getUnidades(numero)
    else if (numero.toInt() > 19) //para 20...99
    {
        val u = getUnidades(numero)
        if (u == "cero") //para 20,30,40,50,60,70,80,90
            decenas[numero.substring(0, 1).toInt() + 8]
        else
            decenas[numero.substring(0, 1).toInt() + 8] + "y " + u
    }
    else //numeros entre 11 y 19
        decenas[numero.toInt() - 10]
}

fun getCentenas(numero : String) : String
{
    val centenas = arrayOf("", "ciento ", "doscientos ", "trecientos ", "cuatrocientos ", "quinientos ", "seiscientos ",
        "setecientos ", "ochocientos ", "novecientos ")

    return if (numero.toInt() > 99) //es centena
        if (numero.toInt() == 100) //caso especial
            "cien"
        else
            centenas[numero.substring(0, 1).toInt()] + getDecenas(numero.substring(1))
    else //se quita el 0 antes de convertir a decenas
        getDecenas(numero)
}

fun getMiles(numero : String) : String
{
    val c = numero.substring(numero.length - 3) //obtiene las centenas
    val m = numero.substring(0, numero.length - 3) //obtiene los miles
    val n : String

    return if (Integer.parseInt(m) > 0)  //se comprueba que miles tenga valor entero
    {
        n = getCentenas(m)
        if(n == "uno ")
            "mil " + getCentenas(c)
        else
            n + "mil " + getCentenas(c)
    }
    else
        "" + getCentenas(c)
}

fun getMillones(numero : String) : String
{
    val miles = numero.substring(numero.length - 6) //se obtiene los miles
    val millon = numero.substring(0, numero.length - 6)//se obtiene los millones

    val n : String = if (millon.length > 1)
        getCentenas(millon) + "millones "
    else
        getUnidades(millon) + "millon "

    return n + getMiles(miles)
}