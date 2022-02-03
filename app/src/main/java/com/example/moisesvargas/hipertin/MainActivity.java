package com.example.moisesvargas.hipertin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    PedidoFragmento frgPedido;
    ClienteFragmento frgCliente;
    MenuItem item;
    SearchView searchView;
    Button button1;
    TabLayout tabLayout;
    TabItem tbCliente;
    MenuItem itTodos,itLunes,itMartes,itMiercoles,itJueves,itViernes,itSabado,itDomingo,itSincronizar;


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tbCliente = findViewById(R.id.tbCliente);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

//        tabLayout.setupWithViewPager(viewPager,true);
//        tabLayout.setSelected(true);

        tabLayout.setTabTextColors(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorAccent));



        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));

    }

    Animation slide;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        item = menu.findItem(R.id.menuSearch);
        itTodos = menu.findItem(R.id.itTodos);
        itLunes = menu.findItem(R.id.itLunes);
        itMartes = menu.findItem(R.id.itMartes);
        itMiercoles = menu.findItem(R.id.itMiercoles);
        itJueves = menu.findItem(R.id.itJueves);
        itViernes = menu.findItem(R.id.itViernes);
        itSabado = menu.findItem(R.id.itSabado);
        itDomingo= menu.findItem(R.id.itDomingo);
        itSincronizar = menu.findItem(R.id.itSincronizar);



        searchView = (SearchView) item.getActionView();
        button1 = (Button) itSincronizar.getActionView();
        searchView.onActionViewCollapsed();

        itTodos.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                frgCliente.filtrarCliente(0);
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                tab.setText("Todos");
                return false;
            }
        }) ;
        itLunes.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                frgCliente.filtrarCliente(1);
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                tab.setText("Lunes");
                return false;
            }
        }) ;
        itMartes.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                frgCliente.filtrarCliente(2);
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                tab.setText("Martes");
                return false;
            }
        }) ;
        itMiercoles.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                frgCliente.filtrarCliente(3);
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                tab.setText("Miercoles");
                return false;
            }
        }) ;
        itJueves.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                frgCliente.filtrarCliente(4);
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                tab.setText("Jueves");
                return false;
            }
        }) ;
        itViernes.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                frgCliente.filtrarCliente(5);
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                tab.setText("Viernes");
                return false;
            }
        }) ;
        itSabado.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                frgCliente.filtrarCliente(6);
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                tab.setText("Sabado");
                return false;
            }
        }) ;
        itDomingo.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                frgCliente.filtrarCliente(7);
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                tab.setText("Domingo");
                return false;
            }
        }) ;
        itSincronizar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent carrito = new Intent(MainActivity.this,PopupSincronizacion.class);
                startActivity(carrito);
                return false;
            }
        }) ;


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose()
            {
                tabLayout.setVisibility(View.VISIBLE);
                return false;
            }
        });
        searchView.setOnSearchClickListener(new SearchView.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                tabLayout.setVisibility(View.GONE);
               if(tabLayout.getSelectedTabPosition()==0)
                {
                    searchView.setQueryHint("Buscar Cliente");
                }
                else
                {
                    searchView.setQueryHint("Buscar Pedido");
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
               frgCliente.miadapatador.getFilter().filter(newText);
               frgPedido.miadapatadorPedido.getFilter().filter(newText);
               return false;
            }
        });


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            switch (position)
            {
                case 0:
                    frgCliente = new ClienteFragmento();
                    return frgCliente;

                case 1:
                    frgPedido = new PedidoFragmento();
                    return frgPedido;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position)
            {
                case 0:
                    return "Clientes";

                case 1:
                    return "Pedidos";
            }
            return null;

        }
    }
}
