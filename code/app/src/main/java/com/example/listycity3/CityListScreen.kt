package com.example.listycity3
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.clickable

@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit,
    replaceCity: (City, City) -> Unit,
    modifier: Modifier = Modifier
) {
    var newCityName by remember { mutableStateOf("") }
    var newProvinceName by remember { mutableStateOf("") }
    var replaceCityName by remember { mutableStateOf("") }
    var replaceProvinceName by remember { mutableStateOf("") }
    var showAddCityFields by remember { mutableStateOf(false) }
    var selectedCity by remember { mutableStateOf<City?>(null) }

    Column(modifier = modifier.fillMaxSize())  {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
        }
        FloatingActionButton(
            modifier = Modifier.padding(16.dp),
            onClick = {
                showAddCityFields = !showAddCityFields
            }
        ) {Text("+")
        }

        //START OF ADDCITY
        if (showAddCityFields) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {OutlinedTextField(
            value = newCityName,
            onValueChange = { newCityName = it },
            label = { Text("City") },
            modifier = Modifier.weight(1f)
        )
            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = newProvinceName,
                onValueChange = { newProvinceName = it },
                label = { Text("Province") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                modifier = Modifier.padding(vertical = 12.dp),
                onClick = {
                    if (newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
                        onAddCity(
                            City(
                                name = newCityName,
                                province = newProvinceName
                            )
                        )
                        newCityName = ""
                        newProvinceName = ""
                        showAddCityFields = false
                    }
                }
            ) {Text("Add City")
            }

        }}
        ////////End of ADDCITY

        /////START OF REPLACE CITY
        if (selectedCity != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {OutlinedTextField(
                value = replaceCityName,
                onValueChange = { replaceCityName = it },
                label = { Text("Replace City") },
                modifier = Modifier.weight(1f)
            )
                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = replaceProvinceName,
                    onValueChange = { replaceProvinceName = it },
                    label = { Text("Replace Province") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        if (replaceCityName.isNotBlank() && (selectedCity != null) && replaceProvinceName.isNotBlank()) {
                            val replacementCity = City(replaceCityName, replaceProvinceName)
                            replaceCity(selectedCity!!, replacementCity)

                            selectedCity = null

                        }
                    }
                ) {Text("Replace City")
                }

            }}
        ////end OF REPLACE CITY


        LazyColumn(modifier = Modifier.fillMaxSize()) {

        itemsIndexed(cities) { index, city ->

            // The following implementation is following a structure generated by Anthropic, Claude Sonnet 5, "have userdefined composable function in kotlin, want to make it clickable. currently i think what i'm doing is passing the modifier with the clickable trait to the function and achieving nothing", 2026-09-17
            CityRow(city = city, onClick = {
                    //when the row is clicked we need to tell selected city
                    if (selectedCity == city) { selectedCity = null
                    }
                    else {selectedCity = city
                        replaceCityName = city.name
                        replaceProvinceName = city.province}


                }
                    )




            if (index < cities.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}}

@Composable
fun CityRow(city: City, onClick: (City) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .clickable{onClick(city)}
    ) {
        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ),
            onAddCity = {},
            replaceCity = { city: City, city1: City -> })
    }
}