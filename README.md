[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/8V48kJUe)
[![Work in MakeCode](https://classroom.github.com/assets/work-in-make-code-8824cc13a1a3f34ffcd245c82f0ae96fdae6b7d554b6539aec3a03a70825519c.svg)](https://classroom.github.com/online_ide?assignment_repo_id=18242956&assignment_repo_type=AssignmentRepo)
# Ödev 3

Bu ödevdeki göreviniz `Rent a Car` uygulaması için backend bir uygulama geliştirmek. Görevleriniz:

- Servis katmanının geliştirmesini tamamlamak.
- Controller katmanının geliştirmesini tamamlamak.
- Controller'lara ait Mock test'lerinin geliştirmesini tamamlamak.

Puanlama:

- mvn install başarılı çalışması => 10 puan
- mvn test başarılı çalışması => 90 puan

Kurallar:

- Clean code prensiplerine uyulmadığı durumlarda => -1 puan uygulanacaktır.
- [Ödev'de Yer Alan Testler](#ödevde-yer-alan-testler) başlığında yer alan testlere ek olarak test ekleyebilirsiniz. Ancak mevcut testleri kaldıramazsınız. (Bu madde'ye uyulmayan bir durum yakaladığımızda `mvn test` görev adımını başarısız sayacağız.) => -90 puan uygulanacaktır.
- `DOKUNULMAMASI` gereken paketler ve dosyalar: (Bu kurala uyulmadığı durumda ödevinizden 0 puan alacaksınız.)
  - dto
  - exception
  - `pom.xml`

## Ödev'de Yer Alan Testler

- BookingControllerTest
  - getBookings_ShouldReturnOk
  - createBooking_ShouldReturnCreated
  - getUserBookings_ShouldReturnOk
  - getUserBookings_ShouldReturnBadRequest
  - cancelBooking_ShouldReturnOk
  - cancelBooking_ShouldReturnBadRequest
- CarControllerTest
  - getAllCars_ShouldReturnOk
  - createCar_ShouldReturnCreated
  - updateCar_ShouldReturnAccepted
  - updateCar_ShouldReturnBadRequest
  - getCar_ShouldReturnOk
  - getCar_ShouldReturnBadRequest
- SearchControllerTest
  - searchAvailableCars_ShouldReturnOk
  - searchAvailableCars_ShouldReturnBadRequest
  - searchByPriceRange_ShouldReturnOk
  - searchByPriceRange_ShouldReturnBadRequest
- UserControllerTest
  - getUsers_ShouldReturnOk
  - registerUser_ShouldReturnCreated
  - getUserProfile_ShouldReturnOk
  - getUserProfile_ShouldReturnBadRequest
  - updateUser_ShouldReturnOk
  - updateUser_ShouldReturnBadRequest

Başarılar dilerim.
