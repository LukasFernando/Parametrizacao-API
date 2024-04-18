<script setup>
// eslint-disable-next-line import/no-unresolved
import { hexToRgb } from '@layouts/utils'
import VueApexCharts from 'vue3-apexcharts'
import { useTheme } from 'vuetify'
import { onMounted, ref, computed } from 'vue'
import axios from 'axios'

const vuetifyTheme = useTheme()
const titulo = ref(null)
onMounted(() => {
  titulo.value = 'danko'
})


var orders = []
var series = []
var labels = []
var colors = []

function startDashboardVariables(){
  const currentTheme = vuetifyTheme.current.value.colors
  axios
    .get('http://localhost:8080/dash/opntrack/visualization')
    .then((response) => {
      const data = response.data;
      for(let key in data){
        const colorStr = data[key]['avatarColor']
        orders.push(data[key])
        series.push(parseInt(data[key]['amount']))
        labels.push(data[key]['title'])
        colors.push(
          colorStr === 'success' ? currentTheme.success : 
          colorStr === 'primary' ? currentTheme.primary : 
          colorStr === 'secondary' ? currentTheme.secondary : 
          colorStr === 'info' ? currentTheme.info : currentTheme.warning
        )
      }
      // console.log(orders)
      // console.log(series)
      // console.log(labels)
      
    })
    .catch((error) => {
      console.log(error)
    })
}

startDashboardVariables()

function getPercentage(){
  return '30%'
}


// const series = [
//   95,
//   80,
//   20,
//   40,
// ]

// const orders = [
//   {
//     amount: '82',
//     title: 'Cloud Build',
//     avatarColor: 'primary',
//     subtitle: 'Descrição Da Track',
//     avatarIcon: 'bx-bar-chart-alt-2',
//   },
//   {
//     amount: '80',
//     title: 'Cloud Service',
//     avatarColor: 'success',
//     subtitle: 'Descrição Da Track',
//     avatarIcon: 'bx-bar-chart-alt-2',
//   },
//   {
//     amount: 73,
//     title: 'Cloud Service',
//     avatarColor: 'info',
//     subtitle: 'Descrição Da Track',
//     avatarIcon: 'bx-bar-chart-alt-2',
//   },
//   {
//     amount: 69,
//     title: 'License and Hardware',
//     avatarColor: 'secondary',
//     subtitle: 'Descrição Da Track',
//     avatarIcon: 'bx-bar-chart-alt-2',
//   },
// ]


const chartOptions = computed(() => {
  const currentTheme = vuetifyTheme.current.value.colors
  const variableTheme = vuetifyTheme.current.value.variables
  const disabledTextColor = `rgba(${ hexToRgb(String(currentTheme['on-surface'])) },${ variableTheme['disabled-opacity'] })`
  const primaryTextColor = `rgba(${ hexToRgb(String(currentTheme['on-surface'])) },${ variableTheme['high-emphasis-opacity'] })`
  
  return {
    chart: {
      sparkline: { enabled: true },
      animations: { enabled: false },
    },
    stroke: {
      width: 6,
      colors: [currentTheme.surface],
    },
    legend: { show: false },
    tooltip: { enabled: true },
    dataLabels: { enabled: false },
    labels: labels,
    // labels: [
    //   'Cloud Build',
    //   'Cloud Sell',
    //   'Cloud Service',
    //   'License and Hardware',
    // ],
    colors: colors,
    // colors: [
    //   currentTheme.success,
    //   currentTheme.primary,
    //   currentTheme.secondary,
    //   currentTheme.info,
    // ],
    grid: {
      padding: {
        top: -7,
        bottom: 5,
      },
    },
    states: {
      hover: { filter: { type: 'none' } },
      active: { filter: { type: 'none' } },
    },
    plotOptions: {
      pie: {
        expandOnClick: false,
        donut: {
          size: '75%',
          labels: {
            show: true,
            name: {
              offsetY: 17,
              fontSize: '14px',
              color: disabledTextColor,
              fontFamily: 'Public Sans',
            },
            value: {
              offsetY: -17,
              fontSize: '24px',
              color: primaryTextColor,
              fontFamily: 'Public Sans',
            },
            total: {
              show: true,
              label: 'Essa Semana',
              fontSize: '10px',
              formatter: () => getPercentage(),
              color: disabledTextColor,
              fontFamily: 'Public Sans',
            },
          },
        },
      },
    },
  }
})

</script>

<template>
<div>
  <VCard>
    <VCardItem class="pb-3">
      <VCardTitle class="mb-1">
        Informmações Tracks
      </VCardTitle>
      <VCardSubtitle>Porcentagem De Parcerios Nas Tracks</VCardSubtitle>

      <template #append>
        <div class="me-n3 mt-n8">
          <MoreBtn :menu-list="moreList" />
        </div>
      </template>
    </VCardItem>

    <VCardText>
      <div class="d-flex align-center justify-space-between mb-3">
        <div class="flex-grow-1">
          <h4 class="text-h4 mb-1">
            {{ labels.length }}
          </h4>
          <span>Total De Tracks</span>
        </div>

        <div>
          <VueApexCharts
            type="donut"
            :height="125"
            width="105"
            :options="chartOptions"
            :series="series"
          />
        </div>
      </div>

      <VList class="card-list mt-7">
        <VListItem
          v-for="order in orders"
          :key="order.title"
        >
          <template #prepend>
            <VAvatar
              rounded
              variant="tonal"
              :color="order.avatarColor"
            >
              <VIcon :icon="order.avatarIcon" />
            </VAvatar>
          </template>

          <VListItemTitle class="mb-1">
            {{ order.title }}
          </VListItemTitle>
          <VListItemSubtitle class="text-disabled">
            {{ order.subtitle }}
          </VListItemSubtitle>

          <template #append>
            <span>{{ order.amount }}</span>
          </template>
        </VListItem>
      </VList>
    </VCardText>
  </VCard>
</div>
</template>

<style lang="scss" scoped>
.card-list {
  --v-card-list-gap: 21px;
}
</style>
