package pl.kate.carapi.service;

import org.springframework.stereotype.Service;
import pl.kate.carapi.model.Car;
import pl.kate.carapi.model.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService{

    private List<Car> carList;

    private CarServiceImpl() {
        this.carList = new ArrayList<>();
        carList.add(new Car(1L, "Fiat", "126p", Color.RED));
        carList.add(new Car(2L, "Polonez", "Caro Plus", Color.BLACK));
        carList.add(new Car(3L, "BMW", "X4", Color.GREEN));
    }

        @Override
        public List<Car> getAllCars() {
            return carList;
        }

        @Override
        public Optional<Car> getCarById(long id) {
            return carList
                    .stream()
                    .filter(car -> car.getId() == id)
                    .findFirst();
        }

        @Override
        public List<Car> getCarsByColor(String color) {
            return carList
                    .stream()
                    .filter(car -> color.toUpperCase().contains(car.getColor().name()))
                    .collect(Collectors.toList());
        }

        @Override
        public boolean addCar(Car car) {
            return carList.add(car);
        }

        @Override
        public Optional<Car> modifyCarPut(Car newCar) {
            Optional<Car> modifyCar = carList.stream()
                    .filter(car -> car.getId() == newCar.getId())
                    .findFirst();
            if(modifyCar.isPresent()){
                modifyCar.get().setMark(newCar.getMark());
                modifyCar.get().setModel(newCar.getModel());
                modifyCar.get().setColor(newCar.getColor());
            }
            return modifyCar;
        }

        @Override
        public Optional<Car> modCar(long id, Car newCar) {
            Optional<Car> modifyCar = carList.stream()
                    .filter(car -> car.getId() == id)
                    .findFirst();
            if(modifyCar.isPresent()){
                if (newCar.getMark() != null) modifyCar.get().setMark(newCar.getMark());
                if (newCar.getModel() != null) modifyCar.get().setModel(newCar.getModel());
                if (newCar.getColor() != null) modifyCar.get().setColor(newCar.getColor());
            }
            return modifyCar;
        }

        @Override
        public boolean removeCar(long id) {
            Optional<Car> deleteCar = getCarById(id);
            return deleteCar.map(car -> carList.remove(car))
                    .orElse(false);
        }
}
