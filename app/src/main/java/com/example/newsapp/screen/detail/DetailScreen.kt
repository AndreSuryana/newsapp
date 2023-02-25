package com.example.newsapp.screen.detail

import android.content.Intent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.ui.component.AppHeader
import com.example.newsapp.ui.theme.SanFrancisco
import com.example.newsapp.util.Ext.formatDateToDaysAgo
import com.example.newsapp.viewmodel.ArticleViewModel
import com.example.newsapp.viewmodel.DetailViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: DetailViewModel = hiltViewModel(),
    sharedViewModel: ArticleViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val lifecycle = LocalLifecycleOwner.current

    val currentArticle by sharedViewModel.article.collectAsState()
    var bookmarked by remember { mutableStateOf(false) }

    currentArticle?.let { article ->
        LaunchedEffect(key1 = true) {
            bookmarked = viewModel.isArticleBookmarked(article)
        }
        Scaffold(
            scaffoldState = scaffoldState,
            modifier = modifier,
            topBar = {
                AppHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    navigationStartIcon = {
                        // Back button
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = "Navigate back"
                            )
                        }
                    },
                    navigationEndIcon = {
                        // Bookmark button
                        IconToggleButton(
                            checked = bookmarked,
                            onCheckedChange = {
                                bookmarked = it
                                viewModel.updateBookmarkArticle(article, bookmarked)
                            }
                        ) {
                            Icon(
                                painter = painterResource(
                                    id = if (bookmarked) R.drawable.ic_bookmark_active
                                    else R.drawable.ic_bookmark
                                ),
                                contentDescription = "Bookmark article"
                            )
                        }

                        // Share button
                        IconButton(
                            onClick = {
                                val intent = Intent.createChooser(
                                    Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, article.articleUrl)
                                        type = "text/plain"
                                    },
                                    "Share article"
                                )
                                context.startActivity(intent)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_share),
                                contentDescription = "Share article"
                            )
                        }
                    }
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
                LaunchedEffect(key1 = viewModel.onBookmarkChanged) {
                    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        launch {
                            viewModel.onBookmarkChanged.collectLatest { bookmarked ->
                                snackbarHostState.showSnackbar(
                                    context.getString(
                                        if (bookmarked) R.string.message_article_bookmarked
                                        else R.string.message_article_bookmark_removed
                                    )
                                )
                            }
                        }
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .scrollable(
                        state = scrollState,
                        orientation = Orientation.Vertical
                    )
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Title
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Start,
                    text = article.title
                )

                // Date & Author
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.subtitle1,
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = SanFrancisco,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        ) {
                            append(article.publishedAt.formatDateToDaysAgo() + " by ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontFamily = SanFrancisco,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        ) {
                            append(article.author ?: stringResource(id = R.string.anonymous))
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Image
                AsyncImage(
                    model = article.imageUrl,
                    contentDescription = article.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .clip(RoundedCornerShape(16.dp)),
                    placeholder = painterResource(id = R.drawable.image_placeholder),
                    error = painterResource(id = R.drawable.image_placeholder)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Content
                Text(
                    text = article.content,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}