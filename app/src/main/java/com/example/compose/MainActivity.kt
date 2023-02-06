package com.example.compose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            val painter = painterResource(id = R.drawable.woman_profile)
//            val description = "This is a sample description"
//            val title = "This is a sample title"
//            Box(modifier = Modifier
//                .fillMaxWidth(0.5f)
//                .padding(16.dp)){
//                ImageCard(
//                    painter = painter,
//                    contentDescription = description,
//                    title = title
//                )
//            }
//            ComposeTheme{
//                Surface(modifier = Modifier.fillMaxWidth()) {
////                    MessageCard(msg = Message("Android","Jetpack Compose"))
//                    Conversation(messages = SampleData.conversationSample)
//                }
//            }
            ComposeTheme {
                    MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTheme {
        Greeting("Android")
    }
}
@Composable
fun ImageCard(
    painter:Painter,
    contentDescription:String,
    title:String,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.fillMaxWidth()
            ,
        shape = RoundedCornerShape(10.dp),
//        elevation = 5.dp
    ) {
        Box(modifier = Modifier
            .height(160.dp)
            .background(Color.Gray)
        ){
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(5.dp)
                )
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 350f
                    )
                ))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ){
                Text(text = title, style = TextStyle(color = Color.White, fontSize = 16.sp))
            }
        }
    }
}
data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message){
    Row(modifier = Modifier.padding(8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.woman_profile),
            contentDescription = "Contact Profile Picture",
            modifier = Modifier
                .size(41.5.dp)
                .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
                .padding(1.5.dp)
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        )
        Column (
            modifier = Modifier.clickable { isExpanded = !isExpanded }
        ){
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleMedium
                )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
//                elevation = 5.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)

            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                    style = MaterialTheme.typography.bodySmall
                )


            }
        }
        
    }
}
@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun PreviewMessageCard(){
    ComposeTheme{
        Surface {
        MessageCard(
            msg = Message("Colleague","Take a look at Jetpack Compose")
        )
        }
    }
}
@Composable
fun Conversation(messages:List<Message>){
    LazyColumn{
        items(messages){message->
            MessageCard(msg = message)
        }
    }
}
@Preview(name = "Light Mode")
@Composable
fun PreviewConversation(){
    ComposeTheme {
        Conversation(messages = SampleData.conversationSample)
    }
}
@Preview(showBackground = true, widthDp = 320)
@Composable
fun MyAppPreview(){
    ComposeTheme {
        MyApp(Modifier.fillMaxSize())
    }
}
@Composable
fun MyApp(modifier: Modifier = Modifier, ) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    Surface(modifier) {
        if (shouldShowOnboarding){
            OnboardingScreen(onContinueClicked = {shouldShowOnboarding = false})
        }else{
            Greetings()
        }
    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names:List<String> = List(1000){"$it"}
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items (items = names){ name->
            Greet(name = name)
        }
    }
}
@Preview(showBackground = true, widthDp = 320, name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GreetingsPreview(){
    ComposeTheme {
        Greetings()
    }
}
@Composable
private fun Greet(name: String){
    Card (
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
            ){
        CardContent(name = name)
    }
}
@Composable
private fun CardContent(name:String){
    var expanded by remember{ mutableStateOf(false)}
    Row(
        modifier = Modifier
            .padding(24.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column (
            modifier = Modifier
                .weight(1f)
                .padding(5.dp)
        ){
            Text(text = "Hello, ")
            Text(
                text = name, style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if(expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if(expanded){
                    stringResource(R.string.show_less)
                }else{
                    stringResource(R.string.show_more)
                }
            )
//                    Text(if(expanded) "Show less" else "Show more")
        }

    }

}
@Composable
fun OnboardingScreen(
    onContinueClicked:()->Unit,
    modifier: Modifier = Modifier){

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text(text = "Continue")
        }
        
    }
}
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview(){
    ComposeTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}