# function used to build the image

function Build-Image {
        param (
            [string]$image_name,
            [string]$image_path
        )
    Write-Host "Building docker image for $image_name" -ForegroundColor Yellow
    cd $image_path

    Write-Host "Generating jar for $image_name" -ForegroundColor Yellow
    # Generate the .jar file by compiling the Java application without output anything
    mvn package -DskipTests | Out-Null
    $image_nam
    Write-Host "Building docker image $image_name" -ForegroundColor Yellow
    # build the docker image
    docker build -t $image_name .

    # Change back to the previous directory
    # Set-Location -Path ..
    cd ..
    Write-Host "Correctly build image $image_name" -ForegroundColor Green
}
# ✔
# define images data to create
$auth_image = "rapidbetauth:latest"
$auth_path = "microservice-auth"
$bets_image = "rapidbetbets:latest"
$bets_path = "microservice-bets"

Build-Image -image_name $auth_image -image_path $auth_path
Build-Image -image_name $bets_image -image_path $bets_path

pause
