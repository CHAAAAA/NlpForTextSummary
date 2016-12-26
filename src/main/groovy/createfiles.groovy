/**
 * createFiles
 */

(21..23).each {
    File i = new File("text${it}.txt")
    i.text = ''

    println(it)
}