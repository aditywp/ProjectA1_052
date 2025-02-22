package com.example.coba_manajemen.view.proyek

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coba_manajemen.R
import com.example.coba_manajemen.model.Proyek
import com.example.coba_manajemen.ui.navigation.DestinasiNavigasi
import com.example.coba_manajemen.viewmodel.PenyediaViewModel
import com.example.coba_manajemen.viewmodel.proyek.HomeProyekViewModel
import com.example.coba_manajemen.viewmodel.proyek.HomeUiState
import com.example.serverdatabasep12.ui.widget.CostumeTopAppBar

object DestinasiProyekHome: DestinasiNavigasi {
    override val route = "home_Proyek"
    override val titleRes = "Home Manajemen Proyek"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeProyekScreen(
    navigateToItemEntry: () -> Unit,
    navigateToTim: () -> Unit,
    navigateToTugas: () -> Unit,
    navigateToAnggota: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    onEditClick: (Int) -> Unit,
    viewModel: HomeProyekViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiProyekHome.titleRes,
                canNavigateBack = false,
                onRefresh = {
                    viewModel.getAllPryk()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(18.dp)
                    .width(150.dp)
                    .height(30.dp),
                containerColor = colorResource(id = R.color.green)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Proyek",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))  // Menambah jarak antara ikon dan teks
                    Text(
                        text = "TAMBAH PROYEK",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }
        },
        bottomBar = {
            // ButtonBar sebagai Bottom Bar
            ButtonBar(
                navigateToTim = navigateToTim,
                navigateToTugas = navigateToTugas,
                navigateToAnggota = navigateToAnggota
            )
        }
    ) { innerPadding ->
        HomeProyekStatus(
            homeUiState = viewModel.prykUIState,
            retryAction = { viewModel.getAllPryk() },
            modifier = Modifier
                .padding(innerPadding)
                .padding(bottom = 100.dp)
                .fillMaxSize(),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deletePryk(it.idProyek)
                viewModel.getAllPryk()
            },
            onEditClick = onEditClick
        )
    }
}

@Composable
fun ButtonBar(
    navigateToTim: () -> Unit = {},
    navigateToTugas: () -> Unit = {},
    navigateToAnggota: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = navigateToTim,
            modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.green))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val timIcon: Painter = painterResource(id = R.drawable.tim)
                Icon(
                    painter = timIcon,
                    contentDescription = "Manajemen Tim",
                    modifier = Modifier.size(30.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Manajemen TIM")
            }
        }

        Button(
            onClick = navigateToTugas,
            modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.green))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val tugasIcon: Painter = painterResource(id = R.drawable.tugas)
                Icon(
                    painter = tugasIcon,
                    contentDescription = "Manajemen Tugas",
                    modifier = Modifier.size(30.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Manajemen Tugas")
            }
        }

        Button(
            onClick = navigateToAnggota,
            modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.green))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Manajemen Anggota",
                    modifier = Modifier.size(30.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Manajemen Anggota")
            }
        }
    }
}

@Composable
fun HomeProyekStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Proyek) -> Unit = {},
    onDetailClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeUiState.Success -> {
            if (homeUiState.proyek.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tidak ada data Proyek")
                }
            } else {
                ProyekLayout(
                    proyek = homeUiState.proyek,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idProyek) },
                    onDeleteClick = { onDeleteClick(it) },
                    onEditClick = {onEditClick(it.idProyek)}
                )
            }
        }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(id = R.drawable.loading),
        contentDescription = "Loading"
    )
}

@Composable
fun OnError(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error),
            contentDescription = "Error"
        )
        Text(
            text = "Failed to load projects",
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text("Retry")
        }
    }
}

@Composable
fun ProyekLayout(
    proyek: List<Proyek>,
    modifier: Modifier = Modifier,
    onDetailClick: (Proyek) -> Unit,
    onDeleteClick: (Proyek) -> Unit = {},
    onEditClick: (Proyek) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(proyek) { proyekItem ->
            ProyekCard(
                proyek = proyekItem,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(proyekItem) },
                onDeleteClick = { onDeleteClick(proyekItem) },
                onDetailClick = {onDetailClick(proyekItem) },
                onEditClick = {onEditClick(proyekItem)}
            )
        }
    }
}

@Composable
fun ProyekCard(
    proyek: Proyek,
    modifier: Modifier = Modifier,
    onDeleteClick: (Proyek) -> Unit = {},
    onDetailClick: (Proyek) -> Unit,
    onEditClick: (Proyek) -> Unit
) {
    var deleteConfirmationRequired by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth())
            {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Build,
                        contentDescription = "Tim Icon",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Blue

                        )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Proyek: ${proyek.namaProyek}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
                Divider(
                    thickness = 2.dp,
                    modifier = Modifier.padding(12.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "ID: ${proyek.idProyek}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Deskripsi: ${proyek.deskripsiProyek}",
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Status: ${proyek.statusProyek}",
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Button(
                        onClick = { onDetailClick(proyek) },
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.green)),
                        modifier = Modifier
                            .height(40.dp)
                            .width(100.dp)
                    ){
                        Text(
                            text = "Detail"
                        )
                    }
                    Button(
                        onClick = { onEditClick(proyek) },
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.green)),
                        modifier = Modifier
                            .height(40.dp)
                            .width(100.dp)
                    ){
                        Text(
                            text = "Update"
                        )
                    }
                    IconButton(
                        onClick = { deleteConfirmationRequired = true },
                        modifier = Modifier
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Project",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }
    }
    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                onDeleteClick(proyek)
            },
            onDeleteCancel = {
                deleteConfirmationRequired = false
            }, modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Delete Data") },
        text = { Text("Are you sure you want to delete this project?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Yes")
            }
        }
    )
}
