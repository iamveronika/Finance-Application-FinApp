package com.example.finapp.data.model

import com.example.finapp.R

enum class TransactionType {
    income,
    outcome,
    INCOME,
    OUTCOME,
    NONE;
}

enum class Category {
    auto,
    food,
    sport,
    house,
    clothes,
    work,
    goal,
    other;


    companion object {

        fun categoryToResource(category: Category): Int {
            return when (category) {
                auto -> R.drawable.ic_action_car
                food -> R.drawable.ic_action_food
                sport -> R.drawable.ic_action_sport
                house -> R.drawable.ic_action_house
                clothes -> R.drawable.ic_action_clother
                work -> R.drawable.ic_action_work
                goal -> R.drawable.ic_action_goal
                other -> R.drawable.ic_action_other
                else -> R.drawable.ic_action_default
            }
        }
    }
}


class Transaction {
    var value: String = ""
    var type: TransactionType = TransactionType.income
    var category: Category = Category.auto
    var time: Long = 0
    fun copy(): Transaction {
        val copy = Transaction()
        copy.time = this.time
        copy.value = this.value
        copy.type = this.type
        copy.category = this.category
        return copy
    }

    constructor()
    constructor(
        value: String,
        type: TransactionType,
        category: Category,
        time: Long
    ) {
        this.value = value
        this.type = type
        this.category = category
        this.time = time
    }

    fun toMap(): Map<String, Any> {
        val transactionMap = mutableMapOf<String, Any>()
        transactionMap["value"] = value
        transactionMap["type"] = type
        transactionMap["category"] = category
        transactionMap["time"] = time
        return transactionMap
    }

    companion object {
        fun fromMap(map: Map<String, Any>): Transaction {
            val transaction = Transaction()
            if (map.containsKey("value"))
                transaction.value = map["value"] as String
            if (map.containsKey("type"))
                transaction.type = TransactionType.valueOf(map["type"] as String)
            if (map.containsKey("category"))
                transaction.category = Category.valueOf(map["category"] as String)
            if (map.containsKey("time"))
                transaction.time = map["time"] as Long

            return transaction
        }
    }
}


