package ke.co.calista.tafuta.model.oauth


class PasswordRecoveryModel {

    var userId: Int? = null
    var userCode: String? = null
    var userFirstName: String? = null
    var userLastName: String? = null
    var userEmail: String? = null
    var message: String? = null


    constructor(userFirstName: String?, userLastName: String?, userEmail: String?, message: String?) {
        this.userFirstName = userFirstName
        this.userLastName = userLastName
        this.userEmail = userEmail
        this.message = message
    }
}