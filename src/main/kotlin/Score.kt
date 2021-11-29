data class Score(val difficulty: String, val score: Int, val date: String, val name: String){
    override fun toString(): String {
        return "$date - $name ($difficulty): $score"
    }
}