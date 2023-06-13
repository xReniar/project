const postsButton = document.querySelector('#postsButton')
const followersButton = document.querySelector('#followersButton')
const followingButton = document.querySelector('#followingButton')

const postContainer = document.querySelector('#postsContainer')
const followersContainer = document.querySelector('#followersContainer')
const followingContainer = document.querySelector('#followingContainer')

window.addEventListener('DOMContentLoaded', function() {
    followersContainer.style.display = 'none';
    followingContainer.style.display =  'none'
});

postsButton.addEventListener('click', ()=>{
    postsContainer.style.display='block'
    followersContainer.style.display='none'
    followingContainer.style.display='none'
})

followingButton.addEventListener('click', ()=>{
    postsContainer.style.display='none'
    followersContainer.style.display='none'
    followingContainer.style.display='flex'

})

followersButton.addEventListener('click', ()=>{
    postsContainer.style.display='none'
    followersContainer.style.display='flex'
    followingContainer.style.display='none'
})