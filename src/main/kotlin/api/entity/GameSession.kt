package api.entity

class GameSession {
    private var score: Int = 0
    private var maxFloor: Int = 0

    fun increaseScore(score: Int) {
        this.score += score
    }

    fun getScore(): Int {
        return score
    }

    fun incrimentFloorCount() {
        this.maxFloor = this.maxFloor.inc()
    }

    fun getMaxFloor(): Int {
        return this.maxFloor
    }
}
