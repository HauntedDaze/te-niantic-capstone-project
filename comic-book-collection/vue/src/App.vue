<template>
  <div id="app">
    <Nav />
    <router-view />
  </div>
</template>

<script>
import Nav from './components/Nav.vue';

export default{
  name: 'app',
  components: {
    Nav,
  },
};
</script>

<style>
/* Main font: Montserrat: logo: Oswald */
@import url('https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;700&family=Oswald&display=swap');

#app {
  --site-red: #E45052;
}

* {
  font-family: 'Montserrat', sans-serif;
}

Nav {
  margin-bottom: 30px;
  display: flex;
  flex-wrap: wrap;
}

body {
  background-color: #faf9f6;
  margin: 0 10%;
  margin-bottom: 2%;
}

form {
  display: flex;
  gap: 20px;
  flex-direction: column;
  padding: 40px;
}

.text-center{
  background-color: #FFFFFF;
  border-radius: 12px;
  box-shadow: 0px 10px 10px rgb(0 0 0 / 20%);
  
  width: 505px;
  height: 580px;

  display: grid;
  justify-self: center;

}

h1 {
  font-style: bold;
}

a {
  color: var(--site-red);
}

input{
  border-radius: 8px;
  height: 50px;
}

button {
  background-color: var(--site-red);
  border: none;
  border-radius: 8px;
  text-align: center;
  height: 50px;
  color: #f7f8f7;

  font-size: 12pt;
  font-weight: 500;
}

.empty-card {
    background-color:#bebebe;
    border: solid 1px;
    width: 200px;
    height: 280px;
    border: 3px solid rgb(109, 109, 109);
    border-radius: 10px;
    margin: 10px;
}

#cancel-button {
    background-color: #ffffff;
    border: solid 2px var(--site-red);
    color: var(--site-red);
}

.card-image {
  width: 200px;
  height: 280px;
  border: 2px solid rgb(46, 46, 46);
  border-radius: 10px;
  margin: 10px;
}

button {
  cursor: pointer;
  text-align: center;
}

.overlay {
  position: fixed;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0,0,0,0.5);
  z-index: 5;
}

.overlay-form{
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%,-50%);
  -ms-transform: translate(-50%,-50%);
}

.form-container {
    max-width: 100%;
    margin: 10px;
    background-color: white;
    border-radius: 10px;
    display: flex;
    flex-direction: column;
    padding: 20px;
    gap: 5px;
}

#banner {
  width: 100%;
  height: 300px;
  /* background-color: rgb(49, 49, 49); */

  display: flex;
  justify-content: center;

  position: relative;
  z-index: 1;

  margin-bottom: 40px;
}
#banner-image {
  width: 100%;
  height: 300px;
  object-fit: cover;
  object-position: 10% 20%;
}
#header-text {
  z-index: 2;
  width: 100%;
  height: 300px;
  position: absolute;
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 0px;
  background-color: rgba(0, 0, 0, 0.5);
}
.page-title {
    text-align: center;
    margin: 10px;
    color: white;
}

.collection-container {
    width: 100%;
    height: auto;
    box-sizing: border-box;

    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    gap: 20px;

    padding: 20px 20px;
    border-radius: 30px;
    background-color: #879fee;
    box-shadow:  0 0 3px -1px transparent, 0 0 2px 1px transparent
        , 0 0 5px 0px transparent, 0px 10px 15px -5px rgb(97, 94, 94)
        , 0 2px 20px -5px rgb(97, 94, 94), 0 0 20px 0px transparent;
}
.hover-shake:hover{
    animation: shake 1s;
    animation-iteration-count: infinite;
}
@keyframes shake {
  0% { transform: translate(0px, 1px) rotate(0deg); }
  12.5% { transform: translate(-1px, -1px) rotate(-1deg); }
  25% { transform: translate(-1px, 1px) rotate(1deg); }
  37.5% { transform: translate(1px, 1px) rotate(0deg); }
  50% { transform: translate(0px, -1px) rotate(1deg); }
  62.5% { transform: translate(-1px, 1px) rotate(-1deg); }
  75% { transform: translate(-1px, 1px) rotate(0deg); }
  87.5% { transform: translate(1px, 0px) rotate(-1deg); }
  100% { transform: translate(-1px, -1px) rotate(1deg); }
}

</style>
