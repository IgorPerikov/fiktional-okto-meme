import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object MathSpek: Spek({
    given("java.util.Math") {
        val a = 1
        val b = 2
        on("Math.max/2") {
            val max = Math.max(a, b)
            it("Should return biggest number") {
                assertThat(max, equalTo(b))
            }
        }
    }
})
