// Este archivo contiene funciones relacionadas con la configuración y
// inicialización de la base de datos, incluyendo migraciones y creación
// de usuarios administrativos por defecto.
//
// Autores:
//   - Carlos Zamudio
//   - Min Che Kim
//   - Jennyfer Jasso
//   - Mariana Barderrabáno

package config

import (
	"backend/database"
	"backend/models"
	"backend/resources/auth"
	"os"
)

// Realiza la migración del esquema de la base de datos.
func Migrate() {
	db := database.GetDatabase()

	// Migrate the schema
	db.AutoMigrate(&models.User{})
	db.AutoMigrate(&models.Client{})
	db.AutoMigrate(&models.Admin{})
	db.AutoMigrate(&models.Category{})
	db.AutoMigrate(&models.Product{})
	db.AutoMigrate(&models.Post{})
	db.AutoMigrate(&models.Cart{})
	db.AutoMigrate(&models.Notifications{})
	db.AutoMigrate(&models.Favorites{})
	db.AutoMigrate(&models.Orders{})
	db.AutoMigrate(&models.Other{})
}

// Crea un administrador por defecto utilizando las variables de entorno ADMIN_EMAIL y ADMIN_PASSWORD.
func CreateDefaultAdmin() {
	email := os.Getenv("ADMIN_EMAIL")
	password := os.Getenv("ADMIN_PASSWORD")

	hashedPassword, err := auth.HashPassword(password)
	if err != nil {
		panic(err)
	}

	_, err = database.GetUserByAdminEmail(email)
	if err != nil {
		database.CreateAdminWithUser(&models.User{
			Email:    email,
			Password: hashedPassword,
		})
	}
}

func CreateDefaultCategories() {
	categories := []models.Category{
		{Name: "Donaciones"},
	}

	for _, category := range categories {
		_, err := database.GetCategoryByName(category.Name)
		if err != nil {
			database.CreateCategory(&category)
		}
	}
}
