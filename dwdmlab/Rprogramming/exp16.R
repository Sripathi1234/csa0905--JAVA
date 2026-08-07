# Input number
num <- as.integer(readline(prompt="Enter a number: "))

# Check odd or even
if(num %% 2 == 0) {
  print("The number is Even")
} else {
  print("The number is Odd")
}