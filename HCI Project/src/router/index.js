import { createRouter, createWebHistory } from 'vue-router'
import PatientHome from '../views/Patient/Home.vue'
import Search from '../views/Patient/Search.vue'
import Main from '../views/Patient/Main.vue'
import Doctor from '../views/Patient/Doctor.vue'
import PatientSignUp from '../views/Patient/PatientSignUp.vue'
import DoctorSignUp from '../views/Doctor/signupDoctor.vue'
import sign from '../views/sign.vue'
import layoutPatient from '../views/Patient/layoutPatient.vue'
import layoutDoctor from '../views/Doctor/layoutDoc.vue'
import clinicAppointments from '../views/Doctor/clinicAppointments.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      component: sign
    },
    {
      path: '/patient',
      children: [
        {
          path: ':id',
          component: PatientHome,
          children: [
            {
              path: 'home',
              component: Main,
            },
            {
              path: 'search/:keytype/:key',
              component: Search
            },
            {
              path:':doctor',
              component: Doctor
            },
            {
              path: 'edit',
              component: layoutPatient
            }
          ]
        },
        {
          path: 'signup',
          component: PatientSignUp
        }
      ]
    },
    {
      path: '/doctor',
      children: [
        {
          path: ':id',
          children: [
            {
              path: 'home',
              component: clinicAppointments
            },
            {
              path: 'edit',
              component: layoutDoctor
            }
          ]
        },
        {
          path: 'signup',
          component: DoctorSignUp
        },
      ]
    }
  ]
})

export default router
