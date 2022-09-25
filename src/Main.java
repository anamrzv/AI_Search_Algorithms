import java.util.*;

public class Main {
    public static void main(String[] args) {
        Graph cities = new Graph(27);
        Vertex vilnus = cities.addVertex("Вильнюс", 1509);
        Vertex brest = cities.addVertex("Брест", 1703);
        Vertex vitebsk = cities.addVertex("Витебск", 1189);
        Vertex voronezh = cities.addVertex("Воронеж", 796);
        Vertex volgograd = cities.addVertex("Волгоград", 847);
        Vertex novgorod = cities.addVertex("Нижний Новгород", 322);
        Vertex daugavpils = cities.addVertex("Даугавпилс", 1404);
        Vertex kaliningrad = cities.addVertex("Калининград", 1804);
        Vertex kaunas = cities.addVertex("Каунас", 1589);
        Vertex kiev = cities.addVertex("Киев", 1369);
        Vertex zhitomir = cities.addVertex("Житомир", 1493);
        Vertex donezk = cities.addVertex("Донецк", 1159);
        Vertex harkov = cities.addVertex("Харьков", 1075);
        Vertex kishinev = cities.addVertex("Кишинев", 1701);
        Vertex spb = cities.addVertex("Санкт-Петербург", 1198);
        Vertex riga = cities.addVertex("Рига", 1536);
        Vertex kazan = cities.addVertex("Казань", 0);
        Vertex minsk = cities.addVertex("Минск", 1389);
        Vertex moscow = cities.addVertex("Москва", 717);
        Vertex murmansk = cities.addVertex("Мурманск", 1670);
        Vertex orel = cities.addVertex("Орел", 900);
        Vertex tallinn = cities.addVertex("Таллинн", 1497);
        Vertex simferopol = cities.addVertex("Симферополь", 1602);
        Vertex yaroslavl = cities.addVertex("Ярославль", 597);
        Vertex ufa = cities.addVertex("Уфа", 449);
        Vertex samara = cities.addVertex("Самара", 296);
        Vertex odessa = cities.addVertex("Одесса", 1639);

        cities.addEdge(vilnus, brest, 531);
        cities.addEdge(vitebsk, brest, 638);
        cities.addEdge(vitebsk, vilnus, 360);
        cities.addEdge(voronezh, vitebsk, 869);
        cities.addEdge(voronezh, volgograd, 581);
        cities.addEdge(volgograd, vitebsk, 1455);
        cities.addEdge(vitebsk, novgorod, 911);
        cities.addEdge(vilnus, daugavpils, 211);
        cities.addEdge(kaliningrad, brest, 699);
        cities.addEdge(kaliningrad, vilnus, 333);
        cities.addEdge(kaunas, vilnus, 102);
        cities.addEdge(kiev, vilnus, 734);
        cities.addEdge(kiev, zhitomir, 131);
        cities.addEdge(zhitomir, donezk, 863);
        cities.addEdge(zhitomir, volgograd, 1493);
        cities.addEdge(kishinev, kiev, 467);
        cities.addEdge(kishinev, donezk, 812);
        cities.addEdge(spb, vitebsk, 602);
        cities.addEdge(spb, kaliningrad, 739);
        cities.addEdge(spb, riga, 641);
        cities.addEdge(moscow, kazan, 815);
        cities.addEdge(moscow, novgorod, 411);
        cities.addEdge(moscow, minsk, 690);
        cities.addEdge(moscow, donezk, 1084);
        cities.addEdge(moscow, spb, 664);
        cities.addEdge(murmansk, spb, 1412);
        cities.addEdge(murmansk, minsk, 2238);
        cities.addEdge(orel, vitebsk, 522);
        cities.addEdge(orel, donezk, 709);
        cities.addEdge(orel, moscow, 368);
        cities.addEdge(odessa, kiev, 487);
        cities.addEdge(riga, kaunas, 267);
        cities.addEdge(tallinn, riga, 308);
        cities.addEdge(harkov, kiev, 471);
        cities.addEdge(harkov, simferopol, 639);
        cities.addEdge(yaroslavl, voronezh, 739);
        cities.addEdge(yaroslavl, minsk, 940);
        cities.addEdge(ufa, kazan, 525);
        cities.addEdge(ufa, samara, 461);

        System.out.println("\nПоиск в глубину:");
        cities.dfs(brest, kazan);
        System.out.println("================================");

        System.out.println("\nПоиск в ширину:");
        cities.setAllUnvisited();
        Queue<Vertex> startCity = new ArrayDeque<>();
        startCity.add(brest);
        cities.bfs(startCity, kazan);
        System.out.println("================================");

        System.out.println("\nПоиск в глубину с ограничением:");
        cities.setAllUnvisited();
        cities.dls(brest, kazan, 1, 11);
        System.out.println("================================");

        System.out.println("\nПоиск в глубину с итеративным углублением:");
        cities.setAllUnvisited();
        cities.iddfs(brest, kazan);
        System.out.println("================================");

        System.out.println("\nДвунаправленный поиск на основе поиска в ширину:");
        cities.setAllUnvisited();
        Queue<Vertex> startCityBi = new ArrayDeque<>();
        Queue<Vertex> targetCityBi = new ArrayDeque<>();
        startCityBi.add(brest);
        targetCityBi.add(kazan);
        cities.bidirectional_search(startCityBi, targetCityBi);
        System.out.println("================================");

        System.out.println("\nBest First Search:");
        cities.setAllUnvisited();
        PriorityQueue<Edge> priority = new PriorityQueue<>();
        priority.add(new Edge(brest, 1));
        cities.best_first_search(priority, kazan);
        System.out.println("================================");

        System.out.println("\nЖадный поиск по первому наилучшему совпадению:");
        cities.setAllUnvisited();
        PriorityQueue<Vertex> priority2 = new PriorityQueue<>();
        priority2.add(brest);
        cities.gfbs(priority2, kazan);
        System.out.println("================================");

        System.out.println("\nA*:");
        cities.setAllUnvisited();
        PriorityQueue<Vertex> priority3 = new PriorityQueue<>(10, new VertexEstimateComparator());
        brest.setEstimationFunction(brest.getHeuristicF());
        priority3.add(brest);
        cities.a_star(priority3, kazan);
        System.out.println("================================");

    }
}